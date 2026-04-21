package com.example.demo01.domains.mongo.HRManagment.HumanResource.service;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.mongo.HRManagment.Department.service.DepartmentModelService;
import com.example.demo01.domains.mongo.HRManagment.Department.service.PositionModelService;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileRequestDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.mapper.StaffProfileMapper;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.model.StaffProfileModels;
import com.example.demo01.repository.mongo.HRManagement.StaffProfileRepository.StaffProfileRepository;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.pagination.sync.PaginatedResponsesIterator;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffProfileServiceImpl implements StaffProfileService {

    @Autowired
    private StaffProfileRepository staffProfileRepository;

    @Autowired
    private StaffProfileMapper staffProfileMapper;


    @Override
    public StaffProfileInfoDto createNewStaffProfile(StaffProfileRequestDto requestDto) {
        Boolean isDefault = requestDto.getIsDefault();
        StaffProfileModels staffProfileModels = staffProfileRepository.findByStaffIdAndIsDefault(requestDto.getStaffId(), true);
        if (isDefault != null && isDefault) {
            if (staffProfileModels != null) {
                staffProfileModels.setIsDefault(false);
                staffProfileRepository.save(staffProfileModels);
            }
        }

        if (staffProfileModels == null && (isDefault == null || !isDefault) ) {
            requestDto.setIsDefault(true);
        }

        StaffProfileModels newItem = staffProfileMapper.fromRequestToEntity(requestDto);
        StaffProfileModels result = staffProfileRepository.save(newItem);
        return staffProfileMapper.fromEntityToDto(result);
    }

    @Override
    public StaffProfileModels getStaffProfileById(String id) {
        return staffProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StaffProfile", "id", id));
    }

    @Override
    public List<StaffProfileInfoDto> getCurrentStaffProfile(String staffId) {
        List<StaffProfileModels> staffProfileInfoDtos = staffProfileRepository.getByStaffId(staffId);
        if (staffProfileInfoDtos.isEmpty()) {
            return List.of();
        }
        return staffProfileMapper.fromEntitiesFromInFoDto(staffProfileInfoDtos);
    }

    @Override
    @Cacheable(value = CacheConstants.STAFF_PROFILE, key = "#id")
    public StaffProfileInfoDto getStaffProfileInfoById(String id) {
        StaffProfileModels staffProfileModels = getStaffProfileById(id);
        return staffProfileMapper.fromEntityToDto(staffProfileModels);
    }

    @Override
    public StaffProfileInfoDto getDefaultStaffProfile(String staffId) {
        StaffProfileModels staffProfileModels = staffProfileRepository.findByStaffIdAndIsDefault(staffId, true);
        return staffProfileMapper.fromEntityToDto(staffProfileModels);
    }

    @Override
    public StaffProfileInfoDto updateStaffProfileInfoById(String id, StaffProfileRequestDto requestDto) {
        StaffProfileModels staffProfileModels = getStaffProfileById(id);
        staffProfileMapper.updateModelFromRequestDto(requestDto, staffProfileModels);
        StaffProfileModels result = staffProfileRepository.save(staffProfileModels);
        return staffProfileMapper.fromEntityToDto(result);
    }

    @Override
    @PreAuthorize("hashRole('ADMIN')")
    public BasePageResponse<StaffProfileInfoDto> getStaffInfoList(FilterWithPagination filterWithPagination) {
        return null;
    }

    @Override
    public void terminateStaffProfile(String id) {

    }
}
