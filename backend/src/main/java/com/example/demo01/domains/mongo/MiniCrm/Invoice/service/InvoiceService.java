package com.example.demo01.domains.mongo.MiniCrm.Invoice.service;

import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.*;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.*;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.model.InvoiceStatus;
import com.example.demo01.utils.FilterRequest;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionRequest;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.PageInput;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public interface InvoiceService {

    InvoiceInfo createInvoiceByPo(InvoiceRequest request);

    InvoiceInfo createInvoiceByPaymentTerm(PaymentCycleDTO paymentCycleDTO);

    InvoiceInfo createInvoiceByRequest(InvoiceRequest invoiceRequest);

    InvoiceInfo updateInvoiceByPo(String invoiceId, TransactionRequest paymentRequest);

    InvoiceInfo invoiceByPo(TransactionRequest paymentRequest);

    InvoiceInfo draftInvoice(TransactionRequest transactionRequest, InvoiceRequest invoiceInfo);

    InvoiceInfo getInvoiceByInvoiceId(String invoiceId);

    InvoiceInfo updateInvoice(String invoiceId, InvoicePatchRequest patchRequest);

    BasePageResponse<InvoiceInfo> getProcessInvoice(Boolean active, List<FilterRequest> filterRequest, PageInput pageInput);

    BasePageResponse<InvoiceInfo> searchInvoice(List<Criteria> criteriaList, List<FilterRequest> filterRequest, Pageable pageable);

    InvoiceResponse getAllInvoice(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<InvoiceInfo> getActiveInvoiceByCustomerId(String customerId);

    List<InvoiceActionResponse> getAvailableActions(InvoiceStatus currentStatus);
}
