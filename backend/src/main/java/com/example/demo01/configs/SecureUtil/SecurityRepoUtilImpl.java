package com.example.demo01.configs.SecureUtil;

import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.core.Auth.dtos.WorkProfile;
import com.example.demo01.core.Exceptions.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SecurityRepoUtilImpl implements SecurityRepoUtil {


    @Override
    public CustomUserDetails getCurrentUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails user) {
            return user;
        }
        throw new InvalidCredentialsException("User not authenticated");
    }

    @Override
    public Boolean isCurrentUserGlobalAdmin() {
        return getCurrentUserDetails().isGlobalAdmin();
    }

    @Override
    public List<String> getCurrentAllowedLocations() {
        return getCurrentUserDetails().getAllowedLocations();
    }

    @Override
    public List<String> getCurrentUserBuIds() {
            List<String> allowedLocations = new ArrayList<>();
            if (isCurrentUserGlobalAdmin()) {
                String defaultBuId = "all";
                allowedLocations.add(defaultBuId);
            } else {
                allowedLocations = getCurrentAllowedLocations();
            }
            return allowedLocations;
    }

    @Override
    public Criteria getSecurityCriteria() {
        if (isCurrentUserGlobalAdmin()) {
            return new Criteria();
        }
        return Criteria.where("buId").in(getCurrentUserBuIds());
    }

    @Override
    public String getCurrentUserId() {
        return getCurrentUserDetails().getStaffId();
    }

    @Override
    public Query createSecureQuery(Query query) {
        Criteria securityCriteria = getSecurityCriteria();
        if (securityCriteria.getCriteriaObject().isEmpty()) {
            return query;
        }
        query.addCriteria(securityCriteria);
        return query;
    }

    @Override
    public List<String> getCurrentDepartmentIds() {
        CustomUserDetails user = getCurrentUserDetails();
            return user.getWorkProfiles().stream()
                    .map(WorkProfile::getDepartmentId)
                    .toList();
    }

    @Override
    public List<String> getCurrentPositionIds() {
        CustomUserDetails user = getCurrentUserDetails();
            return user.getWorkProfiles().stream()
                    .map(WorkProfile::getPositionCode)
                    .toList();
    }

    @Override
    public List<WorkProfile> getCurrentWorkProfiles() {
        return getCurrentUserDetails().getWorkProfiles();
    }
    

    @Override
    public int getViewLevel() {
        CustomUserDetails user = getCurrentUserDetails();
            return user.getWorkProfiles().stream()
                    .mapToInt(WorkProfile::getPositionLevel)
                    .max()
                    .orElse(0);
    }



}
