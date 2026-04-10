package com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.service.Impl;


import com.example.demo01.core.Exceptions.APIException;
import com.example.demo01.core.Exceptions.DuplicateResourceException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagDto;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagRequest;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagUpdateRecord;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.mapper.ProcessTagMapper;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.models.ProcessTag;
import com.example.demo01.repository.mongo.ProcessManagement.ProcessTagRepository.ProcessTagRepository;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.service.ProcessTagService;
import com.example.demo01.utils.*;
import com.example.demo01.utils.Query.Mongo.DynamicQueryCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessTagServiceImpl implements ProcessTagService {

    private final ProcessTagRepository processTagRepository;

    private final ProcessTagMapper processTagMapper;

    private final DynamicQueryCriteria dynamicQueryCriteria;

    private final AppUtil appUtils;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public ProcessTagDto createProcessTag(ProcessTagRequest processTag) {
        try {
            ProcessTag processTagItem = processTagMapper.toProcessTag(processTag);
            String tagNamValue = processTagItem.getTagName().trim().toLowerCase();
            processTagItem.setTagName(tagNamValue);
            ProcessTag savedProcessTag = processTagRepository.save(processTagItem);
            return processTagMapper.toProcessTagDto(savedProcessTag);
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException("ProcessTag with tagName '" + processTag.getTagName() + "' already exists.");
        }
    }

    @Override
    public ProcessTagDto getProcessTagDtoById(String id) {
        ProcessTag processTag = processTagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProcessTag","_id",  id));
        return processTagMapper.toProcessTagDto(processTag);
    }

    @Override
    public ProcessTag getProcessTagById(String id) {
        return processTagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProcessTag","_id",  id));
    }

    @Override
    public BasePageResponse<ProcessTagDto> getAllProcessTags(FilterWithPagination filterWithPagination) {
        List<FilterRequest> filterRequest = filterWithPagination.getFilters();
        PageInput pageInput = filterWithPagination.getPagination();
        List<Criteria> criteriaList = new ArrayList<>();
        Page<ProcessTag> processTags = dynamicQueryCriteria.buildPageResponse(filterRequest, criteriaList,  pageInput, ProcessTag.class);
        return buildPageResponse(processTags);
    }

    @Override
    public BasePageResponse<ProcessTagDto> buildPageResponse(Page<ProcessTag> processTags) {
        List<ProcessTagDto> processTagList = processTagMapper.toProcessTagList(processTags.getContent());
        Pageable pageable = processTags.getPageable();
        return new BasePageResponse<>(
                processTagList,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                processTags.getTotalElements(),
                processTags.getTotalPages(),
                processTags.isLast()
        );
    }

    @Override
    public List<ProcessTagNestFieldDto> getProcessTagsByIds(List<String> ids) {
        List<ProcessTag> processTags = processTagRepository.findAllById(ids);
        processTags.forEach(processTag -> {
            if (processTag == null) {
                throw new APIException("One or more ProcessTags not found for the provided IDs.");
            }
        });
        return processTagMapper.toProcessTagNestFieldDtoList(processTags);
    }

    @Override
    public List<ProcessTagDto> getActiveProcessTagOptions() {
       List<ProcessTag> processTags = processTagRepository.findByActive(true);
        return processTagMapper.toProcessTagList(processTags);
    }
//
//    @Override
//    public List<ProcessTagDto> getActiveProcessTags(Boolean active, FilterWithPagination filter) {
//        List<FilterRequest> filterRequest = filter.getFilters();
//        List<Criteria> criteriaList = new ArrayList<>();
//        criteriaList.add(Criteria.where("active").is(active));
//        Query q = appUtils.applyFilter(filterRequest, criteriaList);
//        List<ProcessTag> processTags = mongoTemplate.find(q,ProcessTag.class);
//        return processTagMapper.toProcessTagList(processTags);
//    }

    @Override
    public ProcessTagDto updateProcessTag(String id, ProcessTagRequest updateRequest) {
        ProcessTag processTag = getProcessTagById(id);
        processTagMapper.updateProcessTagFromDto(updateRequest, processTag);
        ProcessTag savedProcessTag = processTagRepository.save(processTag);
        applicationEventPublisher.publishEvent(new ProcessTagUpdateRecord(
                savedProcessTag.getId(),
                savedProcessTag.getTagName(),
                savedProcessTag.getFullTagName()
        ));
        return processTagMapper.toProcessTagDto(savedProcessTag);
    }

}
