package com.example.demo01.domains.jpa.AssetManagement.Maintenance.utils;

import com.example.demo01.core.Security.utils.SecureUtilMethod.SecurityRepoUtil;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.utils.FilterWithPagination;
import com.example.demo01.utils.ModuleEnum;
import com.example.demo01.utils.Query.PostgreSQL.DynamicSpecificationBuilder;
import com.example.demo01.utils.Query.PostgreSQL.Specification.BuildStaticSpecs;
import com.example.demo01.utils.Query.PostgreSQL.Specification.SpecFieldName;
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

        SpecFieldName fieldName = new SpecFieldName();
        fieldName.setBasementName("locationId");
        fieldName.setAssignedFieldName("assignedTo");

        Map<String, Specification<MaintenanceEntity>> specification = new HashMap<>();

        Specification<MaintenanceEntity> isNotDelete = staticSpecs.isNotDeleted("isDeleted");

        Specification<MaintenanceEntity> buildBaseSpecification = staticSpecs.buildDynamicSpecification(ModuleEnum.MAINTENANCE, fieldName);


        specification.put("base", buildBaseSpecification);
        specification.put("isDeleted", isNotDelete);

        return dynamicSpecificationBuilder.build(filterWithPagination.getFilters(), specification);

    }
}
