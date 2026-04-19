package com.example.demo01.configs.SecureUtil;

import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.core.Auth.dtos.WorkProfile;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface SecurityRepoUtil {

    CustomUserDetails getCurrentUserDetails();

    List<String> getCurrentAllowedLocations();

    List<String> getCurrentDepartmentIds();

    List<String> getCurrentUserBuIds();

    Boolean isCurrentUserGlobalAdmin();

    String getCurrentUserId();

    Criteria getSecurityCriteriaByBu(String buFieldName);

    Query createSecureQuery(Query query, String buFieldName);

    List<String> getCurrentPositionIds();

    List<WorkProfile> getCurrentWorkProfiles();

    WorkProfile getMainCurrentWorkProfile();

    int getViewLevel();

}
