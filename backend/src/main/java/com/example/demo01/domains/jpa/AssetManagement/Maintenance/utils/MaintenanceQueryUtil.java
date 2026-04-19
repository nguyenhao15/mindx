package com.example.demo01.domains.jpa.AssetManagement.Maintenance.utils;

import com.example.demo01.configs.SecureUtil.SecurityRepoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaintenanceQueryUtil {

    @Autowired
    private SecurityRepoUtil securityRepoUtil;

    public <T> Specification<T> buildBase(String columnName) {
        Boolean isGlobalAdmin = securityRepoUtil.isCurrentUserGlobalAdmin();
        List<String> allowBuIds = securityRepoUtil.getCurrentAllowedLocations();
        List<String> positionId =  securityRepoUtil.getCurrentPositionIds();

        return (root, query, criteriaBuilder) -> {
            if (isGlobalAdmin) {
                return criteriaBuilder.conjunction();
            }
            if (allowBuIds == null || allowBuIds.isEmpty()) {
                return criteriaBuilder.disjunction();
            }
            return root.get(columnName).in(allowBuIds);
        };
    }
}
