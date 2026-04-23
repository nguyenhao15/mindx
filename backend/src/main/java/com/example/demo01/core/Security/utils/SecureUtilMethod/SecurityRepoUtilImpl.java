package com.example.demo01.core.Security.utils.SecureUtilMethod;

import com.example.demo01.core.Security.utils.SecurityUtil;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.Department.DepartmentInfoDto;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.Position.PositionDto;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.WorkingField.WorkingFieldDto;
import com.example.demo01.domains.mongo.HRManagment.Department.service.DepartmentModelService;
import com.example.demo01.domains.mongo.HRManagment.Department.service.PositionModelService;
import com.example.demo01.domains.mongo.HRManagment.Department.service.WorkingFieldService;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.service.StaffProfileService;
import com.example.demo01.utils.ScopeView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SecurityRepoUtilImpl implements SecurityRepoUtil {

    @Autowired
    private StaffProfileService staffProfileService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private PositionModelService positionModelService;

    @Autowired
    private DepartmentModelService departmentModelService;

    @Autowired
    private WorkingFieldService workingFieldService;

    @Override
    public Boolean isCurrentUserGlobalAdmin() {
        return securityUtil.getCurrentUserDetails().isGlobalAdmin();
    }

    @Override
    public List<String> getCurrentAllowedLocations() {
        return securityUtil.getCurrentUserDetails().getAllowedLocations();
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
        return securityUtil.getCurrentUserDetails().getStaffId();
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
        return securityUtil.getCurrentUserDetails().getActiveProfile().departmentId();
    }

    @Override
    public String getCurrentPositionIds() {
        return securityUtil.getCurrentUserDetails().getActiveProfile().positionId();
    }

    @Override
    public List<StaffProfileInfoDto> getCurrentWorkProfiles() {
        String userId = getCurrentUserId();
        return staffProfileService.getCurrentStaffProfile(userId);
    }

    @Override
    public StaffProfileInfoDto getMainCurrentWorkProfile() {
       return securityUtil.getCurrentUserDetails().getActiveProfile();
    }

    @Override
    public List<String> getCurrentWorkProfileId() {
        DepartmentInfoDto departmentInfoDto = departmentModelService.getDepartmentByDepartmentCode(getCurrentDepartmentIds());
        List<String> workProfileIds = departmentInfoDto.getWorkingFields()
                .stream()
                .map(WorkingFieldDto::getFieldCode)
                .toList();
        List<WorkingFieldDto> workingProfileIds = workingFieldService.getWorkingFieldByFieldCode(workProfileIds);
        return workingProfileIds.stream()
                .map(WorkingFieldDto::getFieldCode)
                .toList();
    }

    @Override
    public ScopeView buildScopeView() {

        String positionId = getCurrentPositionIds();
        String departmentId = getCurrentDepartmentIds();
        PositionDto positionDto = positionModelService.getPositionDtoByPositionCode(positionId);
        if (positionDto.getScopeView() != null) {
            return positionDto.getScopeView();
        } else {
            DepartmentInfoDto departmentInfoDto = departmentModelService.getDepartmentByDepartmentCode(departmentId);
            if (departmentInfoDto.getScopeView() != null) {
                return departmentInfoDto.getScopeView();
            }
        }

        return ScopeView.SELF;
    }

    @Override
    public int getViewLevel() {
        return securityUtil.getCurrentUserDetails().getActiveProfile().positionLevel();
    }



}
