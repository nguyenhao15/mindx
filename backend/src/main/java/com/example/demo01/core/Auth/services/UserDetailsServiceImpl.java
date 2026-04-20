package com.example.demo01.core.Auth.services;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.core.Auth.models.User;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.service.StaffProfileService;
import com.example.demo01.repository.mongo.CoreRepo.AuthRepositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final StaffProfileService staffProfileService;

    @Override
    @Cacheable(value = CacheConstants.USER_SECURITY_CACHE, key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user,null);
    }
}
