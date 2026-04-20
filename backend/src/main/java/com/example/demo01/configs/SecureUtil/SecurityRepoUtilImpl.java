package com.example.demo01.configs.SecureUtil;

import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.core.Exceptions.InvalidCredentialsException;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.service.StaffProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SecurityRepoUtilImpl implements SecurityRepoUtil {

    @Autowired
    private StaffProfileService staffProfileService;

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
    public Criteria getSecurityCriteriaByBu(String buFieldName) {
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
    public Query createSecureQuery(Query query, String buFieldName) {
        Criteria securityCriteria = getSecurityCriteriaByBu(buFieldName);
        if (securityCriteria.getCriteriaObject().isEmpty()) {
            return query;
        }
        query.addCriteria(securityCriteria);
        return query;
    }

    @Override
    public String getCurrentDepartmentIds() {
        return getCurrentUserDetails().getDepartmentId();
    }

    @Override
    public String getCurrentPositionIds() {
        return getCurrentUserDetails().getPosition();
    }

    @Override
    public List<StaffProfileInfoDto> getCurrentWorkProfiles() {
        return staffProfileService.getCurrentStaffProfile();
    }

    @Override
    public StaffProfileInfoDto getMainCurrentWorkProfile() {
        String userId = getCurrentUserId();
       return staffProfileService.getDefaultStaffProfile();
    }

    @Override
    public int getViewLevel() {
        return getCurrentUserDetails().getPositionLevel();
    }



}
