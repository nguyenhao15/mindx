package com.example.demo01.domains.mongo.HRManagment.HumanResource.service;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.mongo.HRManagment.Department.model.DepartmentModel;
import com.example.demo01.domains.mongo.HRManagment.Department.service.DepartmentModelService;
import com.example.demo01.domains.mongo.HRManagment.Department.service.PositionModelService;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileRequestDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.mapper.StaffProfileMapper;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.model.StaffProfileModels;
import com.example.demo01.repository.mongo.HRManagement.StaffProfileRepository.StaffProfileRepository;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterRequest;
import com.example.demo01.utils.FilterWithPagination;
import com.example.demo01.utils.Query.Mongo.DynamicQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
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

    @Autowired
    private DynamicQueryCriteria dynamicQueryCriteria;

    @Override
    public StaffProfileInfoDto createNewStaffProfile(StaffProfileRequestDto requestDto) {
        Boolean isDefault = requestDto.getIsDefault();
        if (isDefault) {
            validateDefaultStaffProfile(requestDto.getStaffId());
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
    public List<StaffProfileInfoDto> getActiveStaffProfile(String staffId) {
        List<StaffProfileModels>  staffProfileInfoDtos = staffProfileRepository.getByStaffIdAndActive(staffId,true);
        return staffProfileMapper.fromEntitiesFromInFoDto(staffProfileInfoDtos);
    }

    @Override
    public List<StaffProfileInfoDto> getStaffProfileByDepartmentId(String departmentId) {
        List<StaffProfileModels> modelsList = staffProfileRepository.getByDepartmentId(departmentId);
        return staffProfileMapper.fromEntitiesFromInFoDto(modelsList);
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
        if (requestDto.getIsDefault() != null && requestDto.getIsDefault() && !staffProfileModels.getIsDefault()) {
            validateDefaultStaffProfile(requestDto.getStaffId());
        }
        staffProfileMapper.updateModelFromRequestDto(requestDto, staffProfileModels);
        StaffProfileModels result = staffProfileRepository.save(staffProfileModels);
        return staffProfileMapper.fromEntityToDto(result);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BasePageResponse<StaffProfileInfoDto> getStaffInfoList(FilterWithPagination filterWithPagination) {
        List<FilterRequest> filters = filterWithPagination.getFilters();
        List<Criteria> criteria = new ArrayList<>();
        Page<StaffProfileModels> staffProfileModelsPage = dynamicQueryCriteria.buildPageResponse(filters, criteria, filterWithPagination.getPagination(), StaffProfileModels.class);
        return buildPageResponse(staffProfileModelsPage);
    }

    @Override
    public BasePageResponse<StaffProfileInfoDto> buildPageResponse(Page<StaffProfileModels> page) {
        List<StaffProfileInfoDto> content = staffProfileMapper.fromEntitiesFromInFoDto(page.getContent());
        Pageable pageable = page.getPageable();
        return new BasePageResponse<>(
                content,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public void validateDefaultStaffProfile(String staffId) {
        StaffProfileModels staffProfileModels = staffProfileRepository.findByStaffIdAndIsDefault(staffId, true);
            if (staffProfileModels != null) {
                staffProfileModels.setIsDefault(false);
                staffProfileRepository.save(staffProfileModels);
            }
    }

    @Override
    public void terminateStaffProfile(String id) {

    }
}
