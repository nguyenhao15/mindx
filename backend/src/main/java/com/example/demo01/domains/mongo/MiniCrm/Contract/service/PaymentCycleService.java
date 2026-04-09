package com.example.demo01.domains.mongo.MiniCrm.Contract.service;


import com.example.demo01.utils.FilterRequest;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleBulkGen;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCyclePatchRequestDto;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.UpdatePaymentTermDetail;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.PageInput;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public interface PaymentCycleService {

    List<PaymentCycleDTO> handleGeneratePaymentCyclePreview(PaymentCycleBulkGen paymentCycleBulkGen);

    List<PaymentCycleDTO> createPaymentCycles(List<PaymentCycleDTO> paymentCycleDTOList);

    List<PaymentCycleDTO> getPaymentCycleByAppendixId(String appendixId);

    UpdatePaymentTermDetail handleAddPayment(String appendixId, Double addedAmount, Double exchangeRate);

    List<PaymentCycleDTO> refundPaymentHandle(String appendixId, List<TransactionInfoDTO> refundTransactions);

    List<PaymentCycleDTO> handlePaymentWithListTransaction(String appendixId, List<TransactionInfoDTO> transactions);

    BasePageResponse<PaymentCycleDTO> getAllPaymentCycle(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    BasePageResponse<PaymentCycleDTO> getActivePaymentCycle(Boolean done, PageInput pageInput, List<FilterRequest> filterRequest);

    BasePageResponse<PaymentCycleDTO> getPaymentCycleForInvoiceProcess(Boolean done, List<FilterRequest> filterRequest, PageInput pageInput);

    BasePageResponse<PaymentCycleDTO> searchPaymentCycles(List<FilterRequest> filterRequest, Pageable pageable, List<Criteria> criteriaList);

    BasePageResponse<PaymentCycleDTO> getPaymentCycleByBu(String buShortName, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<PaymentCycleDTO> updateActivePaymentCyclesByAppendixId(String appendixId, Boolean active,String updateType);

    List<PaymentCycleDTO> getPaymentCycleByContractId(String contractId);

    PaymentCycleDTO updatePaymentCycle(String id, PaymentCyclePatchRequestDto patchRequestDto);

    String deletePaymentCycle(String id);

    void deletePaymentCyclesByAppendixId(String appendixId);

    Double remainingPaymentByAppendixId(String appendixCode);
}
