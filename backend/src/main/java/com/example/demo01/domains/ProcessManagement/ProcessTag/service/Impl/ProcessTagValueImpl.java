package com.example.demo01.domains.ProcessManagement.ProcessTag.service.Impl;

import com.example.demo01.core.Exceptions.DuplicateResourceException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import com.example.demo01.domains.ProcessManagement.ProcessTag.dtos.processValue.ProcessTagValueDto;
import com.example.demo01.domains.ProcessManagement.ProcessTag.dtos.processValue.ProcessTagValueRequest;
import com.example.demo01.domains.ProcessManagement.ProcessTag.mapper.ProcessTagValueMapper;
import com.example.demo01.domains.ProcessManagement.ProcessTag.models.ProcessTagValue;
import com.example.demo01.repository.mongo.ProcessManagement.ProcessTagRepository.ProcessTagValueRepository;
import com.example.demo01.domains.ProcessManagement.ProcessTag.service.ProcessTagService;
import com.example.demo01.domains.ProcessManagement.ProcessTag.service.ProcessTagValueService;
import com.example.demo01.utils.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.UnknownNullability;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessTagValueImpl implements ProcessTagValueService {

    private final ProcessTagService processTagService;

    private final ProcessTagValueRepository processTagValueRepository;

    private final ProcessTagValueMapper processTagValueMapper;

    private final AppUtil appUtils;

    @Override
    public ProcessTagValueDto createTagValue(ProcessTagValueRequest request) {
        try {
            List<String> tagIds = request.getTagItems().stream()
                    .map(ProcessTagNestFieldDto::getId)
                    .toList();
            List<ProcessTagNestFieldDto> processTags = processTagService.getProcessTagsByIds(tagIds);
            ProcessTagValue tagValue = processTagValueMapper.toEntity(request);
            tagValue.setTagItems(processTags);
            ProcessTagValue savedTagValue = processTagValueRepository.save(tagValue);
            return processTagValueMapper.toDto(savedTagValue);
        } catch (DuplicateKeyException e) {
            System.out.println("Duplicate key error: " + e.getMessage());
            throw new DuplicateResourceException("ProcessTag with tagName '" + request.getTagTitle() + "' already exists.");
        }

    }

    @Override
    public List<ProcessTagValueDto> getProcessTagValueOptions() {
        List<ProcessTagValue> processTagValues = processTagValueRepository.findByActive(true);
        return processTagValueMapper.toDtoList(processTagValues);
    }

    @Override
    public ProcessTagValueDto getTagValueById(String id) {
        ProcessTagValue tagValue = processTagValueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProcessTagValue" ,"_id", id));
        return processTagValueMapper.toDto(tagValue);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public BasePageResponse<ProcessTagValueDto> getAllTagValues(FilterWithPagination filterWithPagination) {
        PageInput  pageInput = filterWithPagination.getPagination();
        List<FilterRequest>  filters = filterWithPagination.getFilters();
        List<Criteria> criteria = new ArrayList<>();
        Page<ProcessTagValue> processTagValues = appUtils.buildPageResponse(filters,criteria, pageInput, ProcessTagValue.class);
        return buildPageResponse(processTagValues);
    }

    @Override
    public BasePageResponse<ProcessTagValueDto> buildPageResponse(@UnknownNullability Page<ProcessTagValue> page) {
        List<ProcessTagValueDto> processTagDtos = processTagValueMapper.toDtoList(page.getContent());

        Pageable pageable = page.getPageable();
        return new BasePageResponse<>(
                processTagDtos,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public ProcessTagValueDto updateTagValue(String id, ProcessTagValueRequest request) {
        ProcessTagValue existingTagValue = processTagValueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProcessTagValue" ,"_id", id));
        processTagValueMapper.updateEntityFromDto(request, existingTagValue);
        ProcessTagValue updatedTagValue = processTagValueRepository.save(existingTagValue);
        return processTagValueMapper.toDto(updatedTagValue);
    }

    @Override
    public ProcessTagValueDto deleteTagValue(String id) {
        ProcessTagValue existingTagValue = processTagValueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProcessTagValue" ,"_id", id));
        processTagValueRepository.delete(existingTagValue);
        return processTagValueMapper.toDto(existingTagValue);
    }

}
