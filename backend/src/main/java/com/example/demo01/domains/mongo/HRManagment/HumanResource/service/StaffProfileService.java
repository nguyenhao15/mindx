package com.example.demo01.domains.mongo.HRManagment.HumanResource.service;

import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileRequestDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.model.StaffProfileModels;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;

import java.util.List;

public interface StaffProfileService {

    CustomUserDetails getCurrentUser();

    StaffProfileInfoDto createNewStaffProfile(StaffProfileRequestDto requestDto);

    StaffProfileModels getStaffProfileById(String id);

    List<StaffProfileInfoDto> getCurrentStaffProfile();

    StaffProfileInfoDto getStaffProfileInfoById(String id);

    StaffProfileInfoDto getDefaultStaffProfile();

    StaffProfileInfoDto updateStaffProfileInfoById(String id, StaffProfileRequestDto requestDto);

    BasePageResponse<StaffProfileInfoDto> getStaffInfoList(FilterWithPagination filterWithPagination);

    void terminateStaffProfile(String id);
}
