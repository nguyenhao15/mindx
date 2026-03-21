package com.example.demo01.domains.ProcessManagemnet.ProcessTag.service;



import com.example.demo01.domains.ProcessManagemnet.ProcessTag.dtos.processValue.ProcessTagValueDto;
import com.example.demo01.domains.ProcessManagemnet.ProcessTag.dtos.processValue.ProcessTagValueRequest;
import com.example.demo01.domains.ProcessManagemnet.ProcessTag.models.ProcessTagValue;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProcessTagValueService {

    ProcessTagValueDto createTagValue(ProcessTagValueRequest request);

    List<ProcessTagValueDto> getProcessTagValueOptions();

    ProcessTagValueDto getTagValueById(String id);

    BasePageResponse<ProcessTagValueDto> getAllTagValues(FilterWithPagination filterWithPagination);

    BasePageResponse<ProcessTagValueDto> buildPageResponse(Page<ProcessTagValue> page);

    ProcessTagValueDto updateTagValue(String id,ProcessTagValueRequest request);

    ProcessTagValueDto deleteTagValue(String id);

}
