package com.example.demo01.domains.MiniCrm.Process.service;

import com.example.demo01.core.AuditUpdate.Dto.AuditUpdateDto;
import com.example.demo01.core.AuditUpdate.model.ChangeType;
import com.example.demo01.core.AuditUpdate.service.AuditItemService;
import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.core.Aws3.service.S3Service;
import com.example.demo01.core.Basement.dto.basement.BUInfoDto;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixRequestFullPayload;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixResponseFullPayload;
import com.example.demo01.utils.*;
import com.example.demo01.domains.MiniCrm.Contract.service.AppendixService;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerInfoDTO;
import com.example.demo01.core.SpaceCustomer.service.CustomerInfoService;
import com.example.demo01.domains.MiniCrm.Process.dtos.ProcessingInfoDto;
import com.example.demo01.domains.MiniCrm.Process.dtos.ProcessingRequest;
import com.example.demo01.domains.MiniCrm.Process.mapper.ProcessingMapper;
import com.example.demo01.domains.MiniCrm.Process.model.ProcessingCollection;
import com.example.demo01.domains.MiniCrm.Process.repository.ProcessingCollectionRepository;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProcessingServiceImpl implements ProcessingService {

    private final ProcessingCollectionRepository processingCollectionRepository;

    private final ProcessingMapper processingMapper;

    private final AppendixService appendixService;

    private final BasementService basementService;

    private final CustomerInfoService customerInfoService;

    private final S3Service s3Service;

    private final MongoTemplate mongoTemplate;

    private final AuditItemService auditItemService;

    private final AppUtil appUtil;

    @Override
//    @Transactional
    public ProcessingInfoDto createProcess(AppendixRequestFullPayload requestFullPayload, @Nullable MultipartFile file) {
        try {
            AppendixResponseFullPayload appendix = appendixService.createAppendix(requestFullPayload, file);
            ProcessingRequest processingRequest = getProcessingRequest(appendix);

            BUInfoDto buInfoDto = basementService.getBuInfoByShortName(processingRequest.getBuId());
            CustomerInfoDTO customerInfoDTO = customerInfoService.findCustomerById(processingRequest.getCustomerId());

            ProcessingCollection created = processingMapper.toEntity(processingRequest);
            ProcessingCollection savedItem = processingCollectionRepository.save(created);
            ProcessingInfoDto processingInfoDto = processingMapper.toDto(savedItem);

            processingInfoDto.setBuName(buInfoDto.getBuFullName());
            processingInfoDto.setCustomerName(customerInfoDTO.getCustomerTitle());
            return processingInfoDto;
        } catch (Exception e ) {
            throw new RuntimeException("Failed to create process: " + e.getMessage(), e);
        }
    }

    private static @NonNull ProcessingRequest getProcessingRequest(AppendixResponseFullPayload appendix) {
        ProcessingRequest processingRequest = new ProcessingRequest();
        AppendixInfoDto appendixInfoDto = appendix.getAppendixInfoDto();
        processingRequest.setProcessCode(appendixInfoDto.getAppendixCode());
        processingRequest.setBuId(appendixInfoDto.getBuId());
        processingRequest.setActive(true);
        processingRequest.setStatus("Waiting");
        processingRequest.setCustomerId(appendixInfoDto.getCustomerId());
        processingRequest.setBuId(appendixInfoDto.getBuId());
        return processingRequest;
    }

    @Override
    public ProcessingInfoDto getProcessById(String id) {
        ProcessingCollection processingCollection = processingCollectionRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Processing_Collection ","_id", id));
        AppendixInfoDto appendixInfoDto = appendixService.getAppendixByAppendixCode(processingCollection.getProcessCode());
        String url = s3Service.getFileUrl(appendixInfoDto.getFileResponseDTO().getFileName(),15L );
        FileResponseDTO fileResponseDTO = appendixInfoDto.getFileResponseDTO();
        fileResponseDTO.setFileUrl(url);

        ProcessingInfoDto processingInfoDto = processingMapper.toDto(processingCollection);
        processingInfoDto.setFile(fileResponseDTO);

        return processingInfoDto;
    }

    @Override
    public BasePageResponse<ProcessingInfoDto> getActiveProcesses(FilterWithPagination filterWithPagination) {
        PageInput pageInput = filterWithPagination.getPagination();
        List<FilterRequest> filterRequests = filterWithPagination.getFilters();
        List<FilterRequest> applyFilters = new ArrayList<>();
        if (filterRequests != null) {
            applyFilters.addAll(filterRequests);
        }


        List<Criteria> baseCriteriaList = new ArrayList<>();
        baseCriteriaList.add(Criteria.where("active").is(true));
        return getProcessesByFilter(applyFilters,baseCriteriaList , pageInput);
    }

    @Override
    public BasePageResponse<ProcessingInfoDto> getProcessesByFilter(List<FilterRequest> filterRequest, List<Criteria> baseCriteriaList, PageInput pageInput) {
        Pageable pageable = pageInput.toPageable();
        Sort sortByAndOrder = pageable.getSort();
        Query query = appUtil.applyFilter(filterRequest, baseCriteriaList).with(pageable).with(sortByAndOrder);

        List<ProcessingCollection> processInfos = mongoTemplate.find(query, ProcessingCollection.class);

        long total = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), ProcessingCollection.class);

        PageImpl<ProcessingCollection> page = new PageImpl<>(processInfos, pageable, total);

        return buildBasePageResponse(page);
    }

    @Override
    public ProcessingInfoDto getProcessByCode(String processCode) {
        ProcessingCollection processingCollection = processingCollectionRepository.getByProcessCode(processCode);
        if (processingCollection == null) {
            throw new ResourceNotFoundException("Processing_Collection ","processCode", processCode);
        }
        return processingMapper.toDto(processingCollection);
    }

    @Override
    public ProcessingInfoDto updateProcess(String id, ProcessingRequest processingRequest) {
        ProcessingCollection processingCollection = processingCollectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Processing_Collection ","_id", id));

        AuditUpdateDto auditUpdateDto = new AuditUpdateDto();
        auditUpdateDto.setDescription(processingRequest.getUpdateDescription());
        auditUpdateDto.setChangeType(ChangeType.UPDATE);
        auditUpdateDto.setEntityId(processingCollection.get_id());
        auditItemService.createAuditItem(auditUpdateDto);

        processingMapper.updateEntityFromDto(processingRequest, processingCollection);
        ProcessingCollection updated = processingCollectionRepository.save(processingCollection);
        return processingMapper.toDto(updated);
    }

    @Override
    public ProcessingInfoDto completedProcess(String id) {
        ProcessingCollection processingCollection = processingCollectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Processing_Collection ","_id", id) );
        processingCollection.setActive(false);
        processingCollection.setStatus("Completed");
        String appendixCode = processingCollection.getProcessCode();
        AuditUpdateDto auditUpdateDto = new AuditUpdateDto();
        auditUpdateDto.setDescription("Mark process as completed, and appendix " + appendixCode + " is activated");
        auditUpdateDto.setChangeType(ChangeType.UPDATE);
        auditUpdateDto.setEntityId(processingCollection.get_id());
        auditItemService.createAuditItem(auditUpdateDto);

        appendixService.activateAppendix(appendixCode, true);

        ProcessingCollection savedItem = processingCollectionRepository.save(processingCollection);

        return processingMapper.toDto(savedItem);
    }

    @Override
    public String deleteProcessById(String id) {
        ProcessingCollection processingCollection = processingCollectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Processing_Collection ","_id", id) );
        AppendixInfoDto appendixInfoDto = appendixService.getAppendixByAppendixCode(processingCollection.getProcessCode());
        AppendixInfoDto updateItem = appendixService.rollBackAppendix(appendixInfoDto.get_id());
        processingCollectionRepository.deleteById(processingCollection.get_id());
        return "Delete successful";
    }

    @Override
    public FileResponseDTO reUploadFileInProcess(String processCode, MultipartFile file) throws IOException {
        AppendixInfoDto appendix = appendixService.getAppendixByAppendixCode(processCode);
        return appendixService.uploadAppendixFile(appendix.get_id(), file);
    }

    private BasePageResponse<ProcessingInfoDto> buildBasePageResponse(Page<ProcessingCollection> processingInfoDtoPage) {


        List<ProcessingInfoDto> processingInfoDto = processingMapper.toDtoList(processingInfoDtoPage.getContent());

        List<String> customerIds = processingInfoDto.stream()
                .map(ProcessingInfoDto::getCustomerId)
                .distinct()
                .toList();

        List<String> buShortNames = processingInfoDto.stream()
                .map(ProcessingInfoDto::getBuId)
                .distinct()
                .toList();

        Map<String, Object> customerNames = customerInfoService.getBatchCustomerTitle(customerIds);
        Map<String, Object> buFullNames = basementService.getBatchBuFullNames(buShortNames);

        List<ProcessingInfoDto> handedArr = processingInfoDto.stream().peek(ap -> {
            String customerName = (String) customerNames.get(ap.getCustomerId());
            String buFullName = (String) buFullNames.get(ap.getBuId());

            ap.setBuName(buFullName);
            ap.setCustomerName(customerName);
        }).toList();

        BasePageResponse<ProcessingInfoDto> basePageResponse = new BasePageResponse<>();
        basePageResponse.setContent(handedArr);
        basePageResponse.setPageNumber(processingInfoDtoPage.getNumber());
        basePageResponse.setPageSize(processingInfoDtoPage.getSize());
        basePageResponse.setTotalElements(processingInfoDtoPage.getTotalElements());
        basePageResponse.setTotalPages(processingInfoDtoPage.getTotalPages());
        return basePageResponse;
    }

}
