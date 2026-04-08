package com.example.demo01.domains.MiniCrm.Contract.service.impl;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.core.Aws3.service.S3ServiceImpl;
import com.example.demo01.core.Basement.model.BranchUnit;
import com.example.demo01.repository.mongo.CoreRepo.BasementRepository.BranchUnitRepository;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixRequest;
import com.example.demo01.domains.MiniCrm.Contract.dtos.contracts.*;
import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.MiniCrm.Contract.mappers.ContractMapper;
import com.example.demo01.domains.MiniCrm.Contract.mappers.PaymentCycleMapper;
import com.example.demo01.domains.MiniCrm.Contract.models.Contract;
import com.example.demo01.domains.MiniCrm.Contract.models.PaymentCycle;
import com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository.ContractRepository;
import com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository.PaymentCycleRepository;
import com.example.demo01.domains.MiniCrm.Contract.service.ContractServices;
import com.example.demo01.core.SpaceCustomer.models.CustomerInfo;
import com.example.demo01.repository.mongo.CoreRepo.SpaceCustomerRepository.CustomerInfoRepository;
import com.example.demo01.domains.MiniCrm.Dimmesion.model.ProductModel;
import com.example.demo01.repository.mongo.MiniCrmRepository.dimRepository.ProductRepository;
import com.example.demo01.domains.MiniCrm.Renting.service.RentingContractService;
import com.example.demo01.core.Exceptions.DuplicateResourceException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.utils.AppUtil;
import com.example.demo01.utils.BasePageResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractServices {

    private final ContractRepository contractRepository;

    private final ContractMapper contractMapper;

    private final PaymentCycleMapper paymentCycleMapper;

    private final AppUtil appUtil;

    private final PaymentCycleRepository paymentCycleRepository;

    private final BranchUnitRepository branchUnitRepository;

    private final RentingContractService rentingContractService;

    private final CustomerInfoRepository customerInfoRepository;

    private final ProductRepository productRepository;

    private final S3ServiceImpl s3Service;

    private static @NotNull ContractDTO generateContractDtoFromAppendixRequest(AppendixRequest appendixRequest) {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractCode(appendixRequest.getContractCode());
        contractDTO.setFileResponseDTO(appendixRequest.getFileResponseDTO());
        contractDTO.setStartDate(appendixRequest.getActivationDate());
        contractDTO.setEndDate(appendixRequest.getEndDate());
        contractDTO.setContractStatus("Active");
        contractDTO.setContractValue(appendixRequest.getTotalValue());
        contractDTO.setBuId(appendixRequest.getBuId());
        contractDTO.setCustomerId(appendixRequest.getCustomerId());
        contractDTO.setServiceId(appendixRequest.getServiceId());
        return contractDTO;
    }

    @Override
    public ContractDTO createPreviewContract(AppendixRequest requestDto) {
        return generateContractDtoFromAppendixRequest(requestDto);
    }

    @Override
    public ContractDTO getContractByCode(String contractCode) {
        Contract contract = contractRepository.findByContractCode(contractCode)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", "contractCode", contractCode));
        return contractMapper.toDto(contract);
    }

    @Override
//    @Transactional
    public ContractDTO createContractToDatabase(ContractRequestDto contractRequestDto) {
        if (Objects.equals(contractRequestDto.getUpdateType(), "NEW")) {
            try {
                Contract createdItem = contractRepository.save(contractMapper.toEntity(contractRequestDto));
                return contractMapper.toDto(createdItem);
            } catch (DuplicateKeyException e) {
                throw new DuplicateResourceException("Contract with the same contract code already exists.");
            }
        } else {
            Contract existingContract = contractRepository.findByContractCode(contractRequestDto.getContractCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Contract", "contractCode", contractRequestDto.getContractCode()));
            return contractMapper.toDto(existingContract);
        }
    }


    @Override
    public ContractFullPayload getContractById(String id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", "_id", id));
        return getContractWithCyclesDTO(contract);
    }

    @Override
    public ContractFullPayload getContractByContractCode(String contractCode) {

        Contract contract = contractRepository.findByContractCode(contractCode)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", "contractCode", contractCode));

        return getContractWithCyclesDTO(contract);
    }

    @NonNull
    private ContractFullPayload getContractWithCyclesDTO(Contract contract) {
        List<PaymentCycle> paymentCycleList = paymentCycleRepository.getByContractId(contract.get_id());

        ContractDTO contractDTO = contractMapper.toDto(contract);
        List<PaymentCycleDTO> paymentCycleDTOList = paymentCycleMapper.toDTOList(paymentCycleList);

        ContractFullPayload contractFullPayload = new ContractFullPayload();
        contractFullPayload.setContractInfo(contractDTO);
        contractFullPayload.setPaymentCycles(paymentCycleDTOList);

        return contractFullPayload;
    }

    @Override
    public BasePageResponse<ContractDTO> getAllContract(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Contract> page = contractRepository.findAll(pageDetails);

        return getContractResponse(page);
    }

    @NonNull
    private BasePageResponse<ContractDTO> getContractResponse(Page<Contract> page) {
        List<Contract> contracts = page.getContent();
        List<ContractDTO> contractDTOS = contractMapper.toDTOList(contracts);

        Set<String> customerIds = contractDTOS.stream()
                .map(ContractDTO::getCustomerId)
                .filter(id -> id != null && !id.isEmpty())
                .collect(Collectors.toSet());

        Set<String> buIds = contractDTOS.stream()
                .map(ContractDTO::getBuId)
                .filter(id -> id != null && !id.isEmpty())
                .collect(Collectors.toSet());

        Set<String> productIds = contractDTOS.stream()
                .map(ContractDTO::getServiceId)
                .filter(id -> id != null && !id.isEmpty())
                .collect(Collectors.toSet());

        List<BranchUnit> branchUnits = branchUnitRepository.findAllById(buIds);
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAllById(customerIds);
        List<ProductModel> productModels = productRepository.findAllById(productIds);

        Map<String, String> customerMap = customerInfoList.stream()
                .collect(Collectors.toMap(CustomerInfo::get_id, CustomerInfo::getCustomerTitle));

        Map<String, String> buMap = branchUnits.stream()
                .collect(Collectors.toMap(BranchUnit::getId, BranchUnit::getBuFullName));

        Map<String, String> productMap = productModels.stream()
                .collect(Collectors.toMap(ProductModel::get_id, ProductModel::getServiceName));

        for (ContractDTO dto : contractDTOS) {
            String customerName = customerMap.getOrDefault(dto.getCustomerId(), "Unknown Customer");
            String buName = buMap.getOrDefault(dto.getBuId(), "Unknown Bu");
            String serviceName = productMap.getOrDefault(dto.getServiceId(), "Unknown Service");
            dto.setCustomerName(customerName);
            dto.setBasementName(buName);
            dto.setServiceName(serviceName);
        }

        BasePageResponse<ContractDTO> contractResponse = new BasePageResponse<>();
        contractResponse.setContent(contractDTOS);
        contractResponse.setPageNumber(page.getNumber());
        contractResponse.setPageSize(page.getSize());
        contractResponse.setTotalElements(page.getTotalElements());
        contractResponse.setTotalPages(page.getTotalPages());
        contractResponse.setLastPage(page.isLast());
        return contractResponse;
    }

    @Override
    public BasePageResponse<ContractDTO> getAllContractByStatus(String contractStatus, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Contract> page = contractRepository.findByContractStatus(contractStatus, pageDetails);

        return getContractResponse(page);
    }

    @Override
    public BasePageResponse<ContractDTO> getAllContractByTenantId(String tenantId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Contract> page = contractRepository.getByCustomerId(tenantId, pageDetails);

        return getContractResponse(page);
    }

    @Override
    public BasePageResponse<ContractDTO> getAllContractByBU(String buShortName, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Contract> page = contractRepository.getByBuId(buShortName, pageDetails);

        return getContractResponse(page);
    }

    @Override
//    @Transactional
    public ContractDTO updateContractById(String id, ContractPatchRequestDto patchRequestDto) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", "_id", id));

        contractMapper.updateContractFromDto(patchRequestDto, contract);

        Contract updatedContract = contractRepository.save(contract);

        return contractMapper.toDto(updatedContract);
    }

    @Override
    public String deleteContractById(String id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", "_id", id));

        List<PaymentCycle> paymentCycleList = paymentCycleRepository.getByContractId(contract.getContractCode());

        contractRepository.deleteById(id);
        rentingContractService.deleteByContractId(contract.getContractCode());
        paymentCycleRepository.deleteAll(paymentCycleList);

        return "Deleted contract successfully!!!";
    }

    @Override
    public void deleteContractByContractCode(String contractCode) {
        contractRepository.deleteByContractCode(contractCode);
    }

    @Override
    public FileResponseDTO uploadContractFile(String contractId, MultipartFile file) throws IOException {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", "_id", contractId));

        String existingFilePath = contract.getContractPath().getFileName();
        if (existingFilePath != null && !existingFilePath.isEmpty()) {
            s3Service.deleteFile(existingFilePath);
        }

        String fileName = contract.getContractCode() + "_" + appUtil.handleSubString(UUID.randomUUID().toString(),5,true);

        FileResponseDTO filePath = s3Service.uploadContractFile(fileName,file);
        contract.setContractPath(filePath);
        contractRepository.save(contract);

        return filePath;
    }
}
