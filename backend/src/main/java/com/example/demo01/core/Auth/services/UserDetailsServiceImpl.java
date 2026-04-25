package com.example.demo01.core.Auth.services;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.core.Auth.models.User;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.dtos.ExceptionPolicyRuleInfoDto;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.service.ExceptionPolicyRuleService;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.service.StaffProfileService;
import com.example.demo01.repository.mongo.CoreRepo.AuthRepositories.UserRepository;
import com.example.demo01.utils.ModuleEnum;
import com.example.demo01.utils.ScopeView;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final StaffProfileService staffProfileService;

    private final ExceptionPolicyRuleService exceptionPolicyRuleService;


    @Override
    @Cacheable(value = CacheConstants.USER_SECURITY_CACHE, key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<StaffProfileInfoDto> staffProfileInfoDtos = staffProfileService.getActiveStaffProfile(username);

        List<ExceptionPolicyRuleInfoDto> exceptions = exceptionPolicyRuleService.getPolicyRuleInfoByUserId(username);

        Map<ModuleEnum, ScopeView> exceptionMap = exceptions.stream()
                .collect(Collectors.toMap(
                        ExceptionPolicyRuleInfoDto::moduleEnum,
                        ExceptionPolicyRuleInfoDto::scopeView
                ));

        return new CustomUserDetails(
                user,
                staffProfileInfoDtos,
                exceptionMap
        );
    }
}
