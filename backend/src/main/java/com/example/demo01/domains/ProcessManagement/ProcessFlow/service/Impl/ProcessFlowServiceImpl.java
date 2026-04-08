package com.example.demo01.domains.ProcessManagement.ProcessFlow.service.Impl;

import com.example.demo01.configs.Constants.FolderConstants;
import com.example.demo01.core.Attachment.dto.AttachmentDto;
import com.example.demo01.core.Attachment.service.AttachmentService;
import com.example.demo01.core.Exceptions.APIException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.ProcessManagement.ProcessFlow.dtos.ProcessFlow.ProcessFlowDto;
import com.example.demo01.domains.ProcessManagement.ProcessFlow.dtos.ProcessFlow.ProcessFlowFullInfoDto;
import com.example.demo01.domains.ProcessManagement.ProcessFlow.dtos.ProcessFlow.ProcessFlowRequest;
import com.example.demo01.domains.ProcessManagement.ProcessFlow.dtos.ProcessFlowTextContent.ProcessFlowContentRequest;
import com.example.demo01.domains.ProcessManagement.ProcessFlow.mapper.ProcessFlowMapper;
import com.example.demo01.domains.ProcessManagement.ProcessFlow.model.ProcessFlow;
import com.example.demo01.domains.ProcessManagement.ProcessFlow.model.ProcessFlowTextContent;
import com.example.demo01.repository.mongo.ProcessManagement.ProcessFlowRepository.ProcessFlowRepository;
import com.example.demo01.domains.ProcessManagement.ProcessFlow.service.ProcessFlowService;
import com.example.demo01.domains.ProcessManagement.ProcessFlow.service.ProcessFlowTextContentService;
import com.example.demo01.domains.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import com.example.demo01.domains.ProcessManagement.ProcessTag.dtos.processValue.ProcessTagValueNestFieldDto;
import com.example.demo01.repository.mongo.ProcessManagement.ProcessTagRepository.ProcessTagRepository;
import com.example.demo01.repository.mongo.ProcessManagement.ProcessTagRepository.ProcessTagValueRepository;
import com.example.demo01.domains.ProcessManagement.ProcessUtils.ProcessManagementUtil;
import com.example.demo01.utils.*;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessFlowServiceImpl implements ProcessFlowService {

    private final ProcessFlowRepository processFlowRepository;

    private final ProcessTagRepository processTagRepository;

    private final ProcessManagementUtil processManagementUtils;

    private final ProcessFlowTextContentService processFlowTextContentService;

    private final AttachmentService attachmentService;

    private final ProcessTagValueRepository processTagValueRepository;

    private final ProcessFlowMapper processFlowMapper;

    private final String ProcessFolder = FolderConstants.PROCESS;

    private final AppUtil appUtils;

    private final MongoTemplate mongoTemplate;

    @Override
    public ProcessFlow getProcessFlowById(String id) {
        return processFlowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProcessFlow not found with id: ","_id", id));
    }

    @Override
    public ProcessFlowDto createProcessFlow(ProcessFlowRequest request, List<MultipartFile> files) {
        ProcessFlow processFlow = processFlowMapper.toProcessFlowFromDto(request);

        List<ProcessTagNestFieldDto> processTagDtos = request.getTagItems();
        List<ProcessTagValueNestFieldDto> processTagValueDtos = request.getTagIdValues();

        List<String> tagIds = processTagDtos.stream().map(ProcessTagNestFieldDto::getId).toList();
        List<String> tagValueIds = processTagValueDtos.stream().map(ProcessTagValueNestFieldDto::getId).toList();

        long existingTagsCount = processTagRepository.countByIdIn(tagIds);
        long existingValuesCount = processTagValueRepository.countByIdIn(tagValueIds);

        if (existingTagsCount != tagIds.size()) {
            throw new APIException("Một hoặc nhiều Process Tag không tồn tại trong hệ thống!");
        }

        if (existingValuesCount != tagValueIds.size()) {
            throw new APIException("Một hoặc nhiều Tag Value không hợp lệ hoặc không tồn tại!");
        }
        ProcessFlow savedProcessFlow = processFlowRepository.save(processFlow);

        ProcessFlowContentRequest processFlowContentRequest = request.getProcessContent();
        processFlowContentRequest.setProcessFlowId(savedProcessFlow.getId());

        attachmentService.addAttachment(files, savedProcessFlow.getId(), ProcessFolder, false);
        processFlowTextContentService.createNewContent(processFlowContentRequest);

        return processFlowMapper.toProcessFlowDto(savedProcessFlow);
    }

    @Override
    public ProcessFlowDto getProcessFlowDtoById(String id) {
        ProcessFlow processFlow = getProcessFlowById(id);

        return processFlowMapper.toProcessFlowDto(processFlow);
    }

    @Override
    public List<ProcessFlowDto> searchFlowItem(String keyword) {
        Query query = new Query();
        if (keyword != null && !keyword.isEmpty()) {
            List<Criteria> baseCriteriaList = new ArrayList<>();

            baseCriteriaList.add(Criteria.where("active").is(true));
            baseCriteriaList.add(processManagementUtils.buildAccessQuery());
            String trimmed = keyword.trim();
            String pattern = Arrays.stream(trimmed.split("\\s+"))
                    .map(Pattern::quote)
                    .collect(Collectors.joining("\\s*"));
            Criteria searchCriteria = new Criteria().orOperator(
                    Criteria.where("title").regex(pattern, "i"),
                    Criteria.where("processDescription").regex(pattern, "i"),
                    Criteria.where("tagItems.tagName").regex(pattern, "i"),
                    Criteria.where("tagIdValues.tagValueCode").regex(pattern, "i")
            );
            baseCriteriaList.add(searchCriteria);

            query.addCriteria(new Criteria().andOperator(baseCriteriaList.toArray(new Criteria[0])));
        }

        query.limit(7);

        List<ProcessFlow> processFlows = mongoTemplate.find(query, ProcessFlow.class);
        return processFlowMapper.toProcessFlowDtoList(processFlows);
    }

    @Override
    public ProcessFlowFullInfoDto getProcessFlowFullInfoById(String id) {
        ProcessFlowDto processFlowDto = getProcessFlowDtoById(id);
        ProcessFlowFullInfoDto fullInfoDto = new ProcessFlowFullInfoDto();
        ProcessFlowTextContent processFlowTextContent = processFlowTextContentService.getProcessFlowTextContent(id);
        List<AttachmentDto> attachments = attachmentService.getAttachmentByOwnerId(id);

        fullInfoDto.setProcessContent(processFlowTextContent);
        fullInfoDto.setAttachments(attachments);
        fullInfoDto.setProcessFlowDto(processFlowDto);
        return fullInfoDto;
    }

    @Override
    public ProcessFlowFullInfoDto updateProcessFlowById(String id, ProcessFlowRequest request,@Nullable List<MultipartFile> files) {
        ProcessFlow processFlow = getProcessFlowById(id);
        processFlowTextContentService.updateProcessFlowTextContent(request.getProcessContent());
        if (files != null) {
            attachmentService.addAttachment(files, id , ProcessFolder, false);
        }
        processFlowMapper.updateProcessFlowFromDto(request, processFlow);
        ProcessFlow updatedProcessFlow = processFlowRepository.save(processFlow);

        return getProcessFlowFullInfoById(updatedProcessFlow.getId());
    }

    @Override
    public BasePageResponse<ProcessFlowDto> getAllProcessFlows(FilterWithPagination filter) {
        PageInput pageInput = filter.getPagination();
        List<FilterRequest> filterRequests = filter.getFilters();
        List<Criteria> baseCriteriaList = new ArrayList<>();
        Page<ProcessFlow> processFlowPage = appUtils.buildPageResponse(filterRequests, baseCriteriaList, pageInput, ProcessFlow.class);
        return buildProcessFlow(processFlowPage) ;
    }

    @Override
    public BasePageResponse<ProcessFlowDto> getActiveProcessFlow(Boolean active, FilterWithPagination filter) {
        PageInput pageInput = filter.getPagination();
        List<FilterRequest> filterRequests = filter.getFilters();

        List<Criteria> baseCriteriaList = new ArrayList<>();

        baseCriteriaList.add(Criteria.where("active").is(active));

        baseCriteriaList.add(processManagementUtils.buildAccessQuery());

        Page<ProcessFlow> processFlowPage = appUtils.buildPageResponse(filterRequests, baseCriteriaList, pageInput, ProcessFlow.class);
        return buildProcessFlow(processFlowPage);
    }

    @Override
    public ProcessFlowDto activateProcessFlow(String id) {
        ProcessFlow processFlow = getProcessFlowById(id);
        Boolean active = processFlow.getActive();
        processFlow.setActive(!active);
        ProcessFlow updatedProcessFlow = processFlowRepository.save(processFlow);
        return  processFlowMapper.toProcessFlowDto(updatedProcessFlow);
    }

    @Override
    public BasePageResponse<ProcessFlowDto> getProcessFlowByDepartmentId(String departmentId, FilterWithPagination filter) {
        PageInput pageInput = filter.getPagination();
        List<FilterRequest> filterRequests = filter.getFilters();

        List<Criteria> baseCriteriaList = new ArrayList<>();
        baseCriteriaList.add(Criteria.where("tagIdValues.tagValueCode").is(departmentId));

        baseCriteriaList.add(processManagementUtils.buildAccessQuery());

        Page<ProcessFlow> processFlows  = appUtils.buildPageResponse(filterRequests, baseCriteriaList, pageInput, ProcessFlow.class);
        return buildProcessFlow(processFlows);
    }

    @Override
    public BasePageResponse<ProcessFlowDto> getProcessWithProcessing(FilterWithPagination filter) {
        PageInput pageInput = filter.getPagination();
        List<FilterRequest> filterRequests = filter.getFilters();

        List<Criteria> baseCriteriaList = new ArrayList<>();
        baseCriteriaList.add(processManagementUtils.buildAccessQuery());
        baseCriteriaList.add(Criteria.where("tagItems.tagName").is("process"));

        Page<ProcessFlow> processFlows =  appUtils.buildPageResponse(filterRequests, baseCriteriaList, pageInput, ProcessFlow.class);
        return buildProcessFlow(processFlows);
    }

    @Override
    public BasePageResponse<ProcessFlowDto> buildProcessFlow(Page<ProcessFlow> processFlows) {
        List<ProcessFlowDto> processFlowDtoList = processFlowMapper.toProcessFlowDtoList(processFlows.getContent());

        Pageable pageable = processFlows.getPageable();

        return new BasePageResponse<>(
               processFlowDtoList,
               pageable.getPageNumber(),
               pageable.getPageSize(),
               processFlows.getTotalElements(),
               processFlows.getTotalPages(),
               processFlows.isLast()
        );
    }

    @Override
    public String deleteProcessFlow(String id) {
        ProcessFlow processFlow = processFlowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProcessFlow not found with id: ","_id", id));
        processFlowRepository.delete(processFlow);
        return "ProcessFlow with id: " + id + " has been deleted successfully.";
    }
}
