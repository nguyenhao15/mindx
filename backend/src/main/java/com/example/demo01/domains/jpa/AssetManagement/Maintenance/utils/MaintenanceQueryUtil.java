package com.example.demo01.domains.jpa.AssetManagement.Maintenance.utils;

import com.example.demo01.core.Security.utils.SecureUtilMethod.SecurityRepoUtil;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.utils.FilterWithPagination;
import com.example.demo01.utils.Query.PostgreSQL.DynamicSpecificationBuilder;
import com.example.demo01.utils.Query.PostgreSQL.Specification.BuildStaticSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MaintenanceQueryUtil {

    @Autowired
    private SecurityRepoUtil securityRepoUtil;

    @Autowired
    private BuildStaticSpecs staticSpecs;

    @Autowired
    private DynamicSpecificationBuilder<MaintenanceEntity> dynamicSpecificationBuilder;

    public Specification<MaintenanceEntity> buildSpecification(FilterWithPagination filterWithPagination) {
        Boolean isGlobalAdmin = securityRepoUtil.isCurrentUserGlobalAdmin();
        StaffProfileInfoDto mainWorkProfile = securityRepoUtil.getMainCurrentWorkProfile();
        String userId = securityRepoUtil.getCurrentUserId();

        Map<String, Specification<MaintenanceEntity>> specification = new HashMap<>();

        Specification<MaintenanceEntity> allow = staticSpecs.validLocation("locationId");

        Specification<MaintenanceEntity> isNotDelete = staticSpecs.isNotDeleted("isDeleted");

        Specification<MaintenanceEntity> isAssign = (root, query, criteriaBuilder) -> {
            if (isGlobalAdmin || !mainWorkProfile.positionId().equals("TECHNICAL_STAFF")  ) {
                return criteriaBuilder.conjunction();
            }
            return root.get("assignedTo").in(userId);
        };


        specification.put("locationId", allow);
        specification.put("isDeleted", isNotDelete);
        specification.put("isAssign", isAssign);

        return dynamicSpecificationBuilder.build(filterWithPagination.getFilters(), specification);

    }
}
