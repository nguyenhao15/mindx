package com.example.demo01.core.Auth.services.impl;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.configs.SecureRepoConfig.SecurityRepoUtil;
import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.core.Auth.dtos.UserDTO;
import com.example.demo01.core.Auth.dtos.WorkProfile;
import com.example.demo01.core.Auth.mapper.UserMapper;
import com.example.demo01.core.Auth.models.Session;
import com.example.demo01.core.Auth.models.User;
import com.example.demo01.core.Auth.repositories.UserRepository;
import com.example.demo01.core.Auth.request.CreateUserRequest;
import com.example.demo01.core.Auth.request.LoginRequest;
import com.example.demo01.core.Auth.response.LoginResponse;
import com.example.demo01.core.Auth.services.RefreshTokenService;
import com.example.demo01.core.Auth.services.UserService;
import com.example.demo01.core.Exceptions.DuplicateResourceException;
import com.example.demo01.core.Exceptions.InvalidCredentialsException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.core.Security.jwt.JwtUtils;
import com.example.demo01.utils.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtUtils jwtUtils;

    private final HttpServletResponse response;

    private final AppUtil appUtil;

    private final SecurityRepoUtil  securityRepoUtil;

    private final HttpServletRequest request;

    private final MongoTemplate mongoTemplate;

    private final AuthenticationManager authenticationManager;

    @Value("${spring.app.tempPassword}")
    private String tempPassword;

    @Value("${spring.app.jwt.refresh-expiration}")
    private int refreshTokenExpirationMs;

    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder encoder;

    private final CacheManager cacheManager;

    @Override
    public UserDTO updateUserRole(String username, String roleName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        user.setSystemRole(roleName);
        User savedInfo = userRepository.save(user);
        evictUserCachesByUsername(savedInfo.getUsername());
        return userMapper.toDto(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
//    @Transactional
    public UserDTO createInternalUser(CreateUserRequest createUserRequest) {
        String staffId = createUserRequest.getStaffId().toUpperCase();
        String emailValue = createUserRequest.getEmail();
        String userFullName = createUserRequest.getFullName();

        if (userRepository.existsByStaffId(staffId)) {
            throw new DuplicateResourceException("Staff Id: " + createUserRequest.getStaffId() + " already exists");
        }
        if (userRepository.existsByEmail(emailValue)) {
            throw new DuplicateResourceException("Email: " + createUserRequest.getEmail() + " already exists");
        }

        String defaultPassword = tempPassword;
        User newUser = new User();
        newUser.setStaffId(staffId);

        newUser.setUsername(staffId);

        newUser.setFullName(userFullName);
        newUser.setEmail(emailValue);
        newUser.setSystemRole(createUserRequest.getSystemRole());

        newUser.setPassword(encoder.encode(defaultPassword));


        if (createUserRequest.getWorkProfileList() != null) {
            List<WorkProfile> profiles = createUserRequest.getWorkProfileList().stream()
                    .map(WorkProfile::new).toList();
            newUser.setWorkProfileList(profiles);
        }
        newUser.setEnabled(true);
        newUser.setAccountNonLocked(true);

        User savedUser = userRepository.save(newUser);
        return userMapper.toDto(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication;
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        boolean isAlreadyExist = userRepository.existsByUsername(username);
        if (!isAlreadyExist) {
            throw new ResourceNotFoundException("Staff", "StaffId", username);
        }

        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException exception) {
            throw new InvalidCredentialsException("Sai thông tin đăng nhập hoặc mật khẩu!!");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtUtils.generateAccessToken(username);

        Session refreshTokenSession = refreshTokenService.createRefreshToken(username);

        setRefreshTokenInCookie(refreshTokenSession.getRefreshToken(), refreshTokenExpirationMs);

        User userInfo = getUserByUsername(username);

        UserDTO userDTO = userMapper.toDto(userInfo);
        userDTO.setWorkProfileList(userDetails.getWorkProfiles());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setUserDTO(userDTO);

        return loginResponse;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYEE')")
    @Override
    public List<UserDTO> searchUser(String keyword) {
        Query query = new Query();

        if (keyword != null && !keyword.isEmpty()) {
            Criteria searchCriteria = new Criteria().orOperator(
                    Criteria.where("staffId").regex(keyword, "i"),
                    Criteria.where("fullName").regex(keyword, "i")
            );
            query.addCriteria(searchCriteria);
        }

        return mongoTemplate.find(query, User.class)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public String refreshToken() {
        String getRefreshTokenValue = getRefreshTokenInCookieValue();
        Session refreshToken = refreshTokenService.findByRefreshToken(getRefreshTokenValue);
        Boolean isValidToken = refreshTokenService.verifyRefreshToken(refreshToken);

        if (!isValidToken) {
            logout();
            throw new InvalidCredentialsException("Refresh Token expired or invalid. Please login again");
        }
        String staffId = refreshToken.getStaffId();

        return jwtUtils.generateAccessToken(staffId);
    }

    @Override
    public User getUserByStaffId(String staffId) {
       return userRepository.findByUsername(staffId)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
    }

    public String logout() {
        String refreshTokenValue = getRefreshTokenInCookieValue();
        if (refreshTokenValue != null) {
            refreshTokenService.deleteRefreshToken(refreshTokenValue);
        }
        setRefreshTokenInCookie("refreshToken", 0);
        SecurityContextHolder.clearContext();
        return "Log out successfully!!";
    }

    private void setRefreshTokenInCookie(String value, int maxAgeSeconds) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(maxAgeSeconds)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private String getRefreshTokenInCookieValue() {
        Cookie cookie = WebUtils.getCookie(request, "refreshToken");
        return cookie != null ? cookie.getValue() : null;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public BasePageResponse<UserDTO> getAllUsers(FilterWithPagination filter) {
        List<FilterRequest> filters = filter.getFilters();
        PageInput pageInput = filter.getPagination();

        List<Criteria> criteria = new ArrayList<>();

        Page<User> userList = appUtil.buildPageResponse(filters, criteria, pageInput, User.class);

        BasePageResponse<UserDTO> userDTOBasePageResponse = new BasePageResponse<>();
        userDTOBasePageResponse.setContent(userList.getContent().stream()
                .map(userMapper::toDto)
                .toList());
        userDTOBasePageResponse.setPageNumber(userList.getNumber());
        userDTOBasePageResponse.setPageSize(userList.getSize());
        userDTOBasePageResponse.setTotalElements(userList.getTotalElements());
        userDTOBasePageResponse.setTotalPages(userList.getTotalPages());
        userDTOBasePageResponse.setLastPage(userList.isLast());

        return userDTOBasePageResponse;
    }

    @Override
    @Cacheable(value = CacheConstants.USER_DETAIL_CACHE, key = "#username")
    public UserDTO getUserInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO getUserDtoByStaffId(String staffId) {
        User user = userRepository.findByStaffId(staffId);
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO updatePassword(String oldPassword, String newPassword) {
        String staffId = securityRepoUtil.getCurrentUserId();
        User user = getUserByStaffId(staffId);
        boolean isValidPassword = encoder.matches(oldPassword, user.getPassword());
        if (!isValidPassword) {
            throw new InvalidCredentialsException("Old password is incorrect");
        }
        user.setPassword(encoder.encode(newPassword));
        User saved = userRepository.save(user);
        evictUserCachesByUsername(saved.getUsername());
        return userMapper.toDto(saved);
    }

    @Override
    public UserDTO updateLockUser(String userId, boolean locked) {
        User user = getUserById(userId);
        user.setAccountNonLocked(locked);
        User updatedUser = userRepository.save(user);
        evictUserCachesByUsername(updatedUser.getUsername());
        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDTO activateUser(String updatePassword) {
        String encodedPassword = encoder.encode(updatePassword);
        String staffId = securityRepoUtil.getCurrentUserId();
        User user = getUserByStaffId(staffId);

        user.setPassword(encodedPassword);
        user.setEnabled(true);

        User updatedUser = userRepository.save(user);

        evictUserCachesByUsername(updatedUser.getUsername());
        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDTO resetPassword(String userId) {
        User user = getUserById(userId);
        user.setPassword(encoder.encode(tempPassword));
        user.setEnabled(false);
        refreshTokenService.deleteRefreshTokenByUserId(userId);
        User updatedUser = userRepository.save(user);
        evictUserCachesByUsername(user.getUsername());
        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDTO updateUserInfo(String userId, UserDTO updateUserRequest) {
        User user = getUserById(userId);
        User updatedUser = userMapper.updateUserInfo(updateUserRequest, user);
        User savedInfo = userRepository.save(updatedUser);
        evictUserCachesByUsername(savedInfo.getUsername());
        return userMapper.toDto(savedInfo);
    }

    @Override
    public void evictUserCachesByUsername(String username) {
        if (username == null || username.isEmpty()) return;
        Cache dtoCache = cacheManager.getCache(CacheConstants.USER_DETAIL_CACHE);
        if (dtoCache != null) dtoCache.evict(username);
        Cache secCache = cacheManager.getCache(CacheConstants.USER_SECURITY_CACHE);
        if (secCache != null) secCache.evict(username);
    }


}
