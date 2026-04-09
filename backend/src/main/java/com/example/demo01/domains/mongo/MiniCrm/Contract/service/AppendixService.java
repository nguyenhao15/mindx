package com.example.demo01.domains.mongo.MiniCrm.Contract.service;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixRequest;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixRequestFullPayload;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixResponseFullPayload;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.terminateCollection.TerminateAction;
import com.example.demo01.domains.mongo.MiniCrm.Contract.models.AppendixStatus;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterRequest;
import com.example.demo01.utils.PageInput;
import org.jspecify.annotations.Nullable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface AppendixService {

    AppendixResponseFullPayload previewAppendix(AppendixRequest appendixRequest);

    AppendixResponseFullPayload createAppendix(AppendixRequestFullPayload requestFullPayload, @Nullable MultipartFile file);

    AppendixInfoDto getAppendixByAppendixCode(String appendixCode);

    AppendixResponseFullPayload getAppendixById(String appendixId);

    AppendixInfoDto getAppendixInfoInGraphById(String appendixCode);

    AppendixInfoDto rollBackAppendix(String appendixCode);

    BasePageResponse<AppendixInfoDto> getActiveAppendix(PageInput pageInput, List<FilterRequest> filter);

    BasePageResponse<AppendixInfoDto> searchAppendix(List<FilterRequest> request, List<Criteria> criteriaList, PageInput pageInput);

    BasePageResponse<AppendixInfoDto> getAllAppendix(PageInput pageInput);

    List<AppendixInfoDto> getActiveAppendixByBuShortNameAndServiceIdAndCustomerId(String buShortName, String serviceId, String customerId);

    AppendixResponseFullPayload handlePaymentAppendix(String appendixId, Double paidAmount, Double exchangeRate);

    List<AppendixInfoDto> getAppendixByCustomerId(String customerId);

    List<AppendixInfoDto> getActiveAppendixByCustomerId(String customerId, String buId);

    List<AppendixInfoDto> getAppendixByContract(String contractCode);

    AppendixInfoDto updateAppendix(String id,AppendixRequest appendixRequest);

    List<AppendixInfoDto> forceUpdateAppendix(String ContractCode);

    AppendixResponseFullPayload activateAppendix(String appendixId,Boolean active);

    void updateUnActiveAppendixByContractId(String appendixCode, AppendixStatus appendixStatus);

    List<TerminateAction> terminateAppendix(String appendixCode, LocalDate terminationDate);

    void deleteAppendix(String appendixId);

    FileResponseDTO uploadAppendixFile(String appendixId, MultipartFile file) throws IOException;

    Double getDiffAmountAppendix(AppendixRequest appendixRequest);

}
