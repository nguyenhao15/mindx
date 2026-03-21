package com.example.demo01.domains.MiniCrm.Process.service;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixRequestFullPayload;
import com.example.demo01.utils.FilterRequest;
import com.example.demo01.domains.MiniCrm.Process.dtos.ProcessingInfoDto;
import com.example.demo01.domains.MiniCrm.Process.dtos.ProcessingRequest;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import com.example.demo01.utils.PageInput;
import org.jspecify.annotations.Nullable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProcessingService {

    ProcessingInfoDto createProcess(AppendixRequestFullPayload requestFullPayload,@Nullable MultipartFile file);

    ProcessingInfoDto getProcessById(String id);

    BasePageResponse<ProcessingInfoDto> getActiveProcesses(FilterWithPagination filterWithPagination);

    BasePageResponse<ProcessingInfoDto> getProcessesByFilter(List<FilterRequest> filterRequest, List<Criteria> baseCriteriaList, PageInput pageInput);

    ProcessingInfoDto getProcessByCode(String processCode);

    ProcessingInfoDto updateProcess(String id, ProcessingRequest processingRequest);

    ProcessingInfoDto completedProcess(String id);

    String deleteProcessById(String id);

    FileResponseDTO reUploadFileInProcess(String processCode, MultipartFile file) throws IOException;

}
