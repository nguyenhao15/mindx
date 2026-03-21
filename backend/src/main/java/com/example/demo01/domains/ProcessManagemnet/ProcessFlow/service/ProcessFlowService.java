package com.example.demo01.domains.ProcessManagemnet.ProcessFlow.service;


import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.dtos.ProcessFlow.ProcessFlowDto;
import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.dtos.ProcessFlow.ProcessFlowFullInfoDto;
import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.dtos.ProcessFlow.ProcessFlowRequest;
import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.model.ProcessFlow;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProcessFlowService {

    ProcessFlow getProcessFlowById(String id);

    ProcessFlowDto createProcessFlow(ProcessFlowRequest request, List<MultipartFile> files);

    ProcessFlowDto getProcessFlowDtoById(String id);

    List<ProcessFlowDto> searchFlowItem(String keyword);

    ProcessFlowFullInfoDto getProcessFlowFullInfoById(String id);

    ProcessFlowFullInfoDto updateProcessFlowById(String id, ProcessFlowRequest request, List<MultipartFile> files);

    BasePageResponse<ProcessFlowDto> getAllProcessFlows(FilterWithPagination filter);

    BasePageResponse<ProcessFlowDto> getActiveProcessFlow(Boolean active, FilterWithPagination filter);

    ProcessFlowDto activateProcessFlow(String id);

    BasePageResponse<ProcessFlowDto> getProcessFlowByDepartmentId(String departmentId, FilterWithPagination filter);

    BasePageResponse<ProcessFlowDto> getProcessWithProcessing(FilterWithPagination filter);

    BasePageResponse<ProcessFlowDto> buildProcessFlow(Page<ProcessFlow> processFlows);

    String deleteProcessFlow(String id);
}
