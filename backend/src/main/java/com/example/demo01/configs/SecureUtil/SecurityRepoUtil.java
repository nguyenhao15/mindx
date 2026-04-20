package com.example.demo01.configs.SecureUtil;

import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface SecurityRepoUtil {

    CustomUserDetails getCurrentUserDetails();

    List<String> getCurrentAllowedLocations();

    String getCurrentDepartmentIds();

    List<String> getCurrentUserBuIds();

    Boolean isCurrentUserGlobalAdmin();

    String getCurrentUserId();

    Criteria getSecurityCriteriaByBu(String buFieldName);

    Query createSecureQuery(Query query, String buFieldName);

    String getCurrentPositionIds();

    List<StaffProfileInfoDto> getCurrentWorkProfiles();

    StaffProfileInfoDto getMainCurrentWorkProfile();

    int getViewLevel();

}
