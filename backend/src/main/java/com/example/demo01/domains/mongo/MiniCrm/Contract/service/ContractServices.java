package com.example.demo01.domains.mongo.MiniCrm.Contract.service;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixRequest;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractFullPayload;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractPatchRequestDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractRequestDto;
import com.example.demo01.utils.BasePageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ContractServices {

    ContractDTO createPreviewContract(AppendixRequest requestDto);

    ContractDTO getContractByCode(String contractCode);

    ContractDTO createContractToDatabase(ContractRequestDto appendixRequest);

    ContractFullPayload getContractById(String id);

    ContractFullPayload getContractByContractCode(String contractCode);

    BasePageResponse<ContractDTO> getAllContract(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    BasePageResponse<ContractDTO> getAllContractByStatus(String contractStatus, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    BasePageResponse<ContractDTO> getAllContractByTenantId(String tenantId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    BasePageResponse<ContractDTO> getAllContractByBU(String buShortName, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ContractDTO updateContractById(String id, ContractPatchRequestDto patchRequestDto);

    String deleteContractById(String id);

    void deleteContractByContractCode(String contractCode);

    FileResponseDTO uploadContractFile(String contractId, MultipartFile file) throws IOException;
}
