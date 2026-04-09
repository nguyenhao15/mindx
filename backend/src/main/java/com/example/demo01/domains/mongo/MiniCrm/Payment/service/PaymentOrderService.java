package com.example.demo01.domains.mongo.MiniCrm.Payment.service;

import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.paymentOrder.*;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.paymentOrder.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PaymentOrderService {

    PaymentOrderWithDetail createNewPaymentOrder(PaymentOrderRequest paymentOrderRequest, List<MultipartFile> file);

    PaymentOrderWithDetail getPaymentOrderById(String paymentId);

    PaymentOrderResponse getAllPaymentOrder(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    PaymentOrderResponse getUnConfirmedPaymentOrder(String confirmStatus, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    PaymentOrderResponse getPaymentOrderByTenantId(String tenantId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    PaymentOrderDTO updatePaymentOrderById(String id, PaymentOrderPatchRequest paymentOrderPatchRequest);

    String deletePaymentOrderById(String id);

}
