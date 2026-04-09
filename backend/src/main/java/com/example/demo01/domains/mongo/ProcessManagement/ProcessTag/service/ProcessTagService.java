package com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.service;


import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagDto;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagRequest;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.models.ProcessTag;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProcessTagService {

    ProcessTagDto createProcessTag(ProcessTagRequest processTag);

    ProcessTagDto getProcessTagDtoById(String id);

    ProcessTag getProcessTagById(String id);

    BasePageResponse<ProcessTagDto> getAllProcessTags(FilterWithPagination filterWithPagination);

    BasePageResponse<ProcessTagDto> buildPageResponse(Page<ProcessTag> processTags);

    List<ProcessTagNestFieldDto> getProcessTagsByIds(List<String> ids);

    List<ProcessTagDto> getActiveProcessTagOptions();

    ProcessTagDto updateProcessTag(String id, ProcessTagRequest updateRequest);
}
