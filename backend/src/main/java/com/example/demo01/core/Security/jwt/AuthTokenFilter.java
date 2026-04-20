package com.example.demo01.core.Security.jwt;

import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.core.Auth.models.User;
import com.example.demo01.core.Auth.services.UserDetailsServiceImpl;
import com.example.demo01.core.Exceptions.InvalidCredentialsException;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.service.StaffProfileService;
import com.example.demo01.repository.mongo.CoreRepo.AuthRepositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StaffProfileService staffProfileService;

    @Value("${PROFILE_HEADER}")
    private String PROFILE_HEADER;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username));
                String activeProfileId = request.getHeader(PROFILE_HEADER);

                StaffProfileInfoDto activeProfileInfo = null;

                if (activeProfileId != null && !activeProfileId.isEmpty()) {
                    activeProfileInfo = staffProfileService.getStaffProfileInfoById(activeProfileId);
                }

                CustomUserDetails contextualUserDetails = new CustomUserDetails(user, activeProfileInfo);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                contextualUserDetails,
                                null,
                                contextualUserDetails.getAuthorities()
                        );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            throw new InvalidCredentialsException(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        return jwtUtils.getJwtFromHeader(request);
    }
}
