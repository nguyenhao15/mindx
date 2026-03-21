package com.example.demo01.domains.MiniCrm.Payment.service;

import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixRequest;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.TransactionPatchRequest;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.TransactionRequest;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterRequest;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    TransactionInfoDTO addPaymentDetail(TransactionRequest transactionRequest);

    TransactionInfoDTO createAPaymentDetail(TransactionRequest transactionRequest);

    TransactionInfoDTO getPaymentDetailById(String id);

    List<TransactionInfoDTO> getTransactionsByPaymentId(String paymentId);

    List<TransactionInfoDTO> getTransactionsForRefundByPaymentId(AppendixRequest appendixRequest, Boolean isReverse);

    List<TransactionInfoDTO> getPaymentDetailByPaymentOrderId(String paymentOrderId);

    BasePageResponse<TransactionInfoDTO> getAllPaymentDetail(FilterWithPagination filterWithPagination);

    BasePageResponse<TransactionInfoDTO> getTransactionWithFilter(Pageable pageable, List<FilterRequest> filterRequests );

    List<TransactionInfoDTO> getPaymentDetailByCustomerId(String customerId);

    TransactionInfoDTO updatePaymentDetail(String id, TransactionPatchRequest transactionPatchRequest);

    List<TransactionInfoDTO> deleteTransactionByPaymentId(String paymentId);

    List<TransactionInfoDTO> getTransactionByInvoiceId(String invoiceId);

    String deletePaymentDetail(String id);

}
