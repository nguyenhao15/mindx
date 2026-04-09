package com.example.demo01.configs.Mongo.SecureRepoConfig;

import com.example.demo01.core.Auth.dtos.CustomUserDetails;
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

    Criteria getSecurityCriteria();

    Query createSecureQuery(Query query);

    List<String> getCurrentPositionIds();

    int getViewLevel();

}
