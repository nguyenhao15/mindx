package com.example.demo01.domains.mongo.HRManagment.HumanResource.service;

import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileRequestDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.model.StaffProfileModels;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;

import java.util.List;

public interface StaffProfileService {

    StaffProfileInfoDto createNewStaffProfile(StaffProfileRequestDto requestDto);

    StaffProfileModels getStaffProfileById(String id);

    List<StaffProfileInfoDto> getCurrentStaffProfile(String staffId);

    List<StaffProfileInfoDto> getActiveStaffProfile(String staffId);

    List<StaffProfileInfoDto> getStaffProfileByDepartmentId(String departmentId);

    StaffProfileInfoDto getStaffProfileInfoById(String id);

    StaffProfileInfoDto getDefaultStaffProfile(String staffId);

    StaffProfileInfoDto updateStaffProfileInfoById(String id, StaffProfileRequestDto requestDto);

    BasePageResponse<StaffProfileInfoDto> getStaffInfoList(FilterWithPagination filterWithPagination);

    void validateDefaultStaffProfile(String staffId);

    void terminateStaffProfile(String id);
}
