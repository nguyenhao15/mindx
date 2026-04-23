package com.example.demo01.core.Security.utils.SecureUtilMethod;

import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.utils.ScopeView;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface SecurityRepoUtil {

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

    List<String> getCurrentWorkProfileId();

    ScopeView buildScopeView();

    int getViewLevel();

}
