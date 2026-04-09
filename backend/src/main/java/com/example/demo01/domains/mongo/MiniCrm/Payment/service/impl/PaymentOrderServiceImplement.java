package com.example.demo01.domains.mongo.MiniCrm.Payment.service.impl;

import com.example.demo01.core.Attachment.service.AttachmentService;
import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.core.Aws3.service.S3Service;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.paymentOrder.*;
import com.example.demo01.repository.mongo.CoreRepo.SpaceCustomerRepository.CustomerInfoRepository;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoiceInfo;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionRequest;
import com.example.demo01.domains.mongo.MiniCrm.Payment.mapper.TransactionMapper;
import com.example.demo01.domains.mongo.MiniCrm.Payment.mapper.PaymentOrderMapper;
import com.example.demo01.domains.mongo.MiniCrm.Payment.models.Transaction;
import com.example.demo01.domains.mongo.MiniCrm.Payment.models.PaymentOrder;
import com.example.demo01.repository.mongo.MiniCrmRepository.paymentRepository.TransactionRepository;
import com.example.demo01.repository.mongo.MiniCrmRepository.paymentRepository.PaymentOrderRepository;
import com.example.demo01.domains.mongo.MiniCrm.Payment.service.CentralPaymentService;
import com.example.demo01.domains.mongo.MiniCrm.Payment.service.TransactionService;
import com.example.demo01.domains.mongo.MiniCrm.Payment.service.PaymentOrderService;
import com.example.demo01.core.Exceptions.APIException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.mongo.MiniCrm.Utils.MC_Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentOrderServiceImplement implements PaymentOrderService {

    private final CustomerInfoRepository customerInfoRepository;

    private final TransactionService transactionService;

    private final TransactionRepository transactionRepository;

    private final PaymentOrderRepository paymentOrderRepository;

    private final PaymentOrderMapper paymentOrderMapper;

    private final TransactionMapper transactionMapper;

    private final CentralPaymentService centralPaymentService;

    private final AttachmentService attachmentService;

    private final S3Service s3Service;

    private final MC_Utils mcUtils;

//    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaymentOrderWithDetail createNewPaymentOrder(PaymentOrderRequest paymentOrderRequest, List<MultipartFile> files) {
        String customerId = paymentOrderRequest.getCustomerId();
        Double paymentAmountValue = paymentOrderRequest.getPaymentAmount();
        LocalDate paymentDate = paymentOrderRequest.getPaymentDate();

        customerInfoRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerId", "_id", customerId));

        if (paymentOrderRequest.getPaymentDate().isAfter(LocalDate.now())) {
            throw new APIException("Date value must before or equal today");
        }

        if (files.isEmpty()) {
            throw new APIException("File is empty");
        }

        List<FileResponseDTO> filePaths = new ArrayList<>();

        List<TransactionRequest> transactionRequests = paymentOrderRequest.getTransactionRequests();

        Double totalAmount = transactionRequests.stream()
                .mapToDouble(TransactionRequest::getAmount)
                .sum();

        Double exchangeRateValue = paymentOrderRequest.getExchangeRate();
        Double localAmountValue = totalAmount * exchangeRateValue;

        paymentOrderRequest.setPaymentAmount(totalAmount);
        paymentOrderRequest.setLocalAmount(localAmountValue);
        paymentOrderRequest.setCurrencyCode(paymentOrderRequest.getCurrencyCode());

        files.forEach((file) -> {
            String fileName = mcUtils.paymentOrderGenTitle(paymentAmountValue,paymentDate,customerId);
            FileResponseDTO filePath = s3Service.uploadPaymentOrderAttachment(fileName, file);
            filePaths.add(filePath);
        });

        paymentOrderRequest.setAttachments(filePaths);

        PaymentOrder createItem = paymentOrderMapper.toEntity(paymentOrderRequest);
        PaymentOrder createdPaymentOrder = paymentOrderRepository.save(createItem);

        String paymentOrderId = createdPaymentOrder.get_id();

        List<Transaction> detailsToSave = new ArrayList<>();

        List<PaymentCycleDTO> paymentCycleDTOS = new ArrayList<>();
        List<InvoiceInfo> invoiceInfos = new ArrayList<>();

        transactionRequests.forEach(pr -> {
            pr.setLocalAmount(pr.getAmount() * exchangeRateValue);
            pr.setDepositRemainAmount(pr.getAmount());
            pr.setPaymentOrderId(paymentOrderId);
            pr.setCustomerId(customerId);
            pr.setPaymentDate(paymentDate);
            pr.setExchangeRate(exchangeRateValue);

            TransactionInfoDTO transactionInfoDTO = centralPaymentService.handlePayment(pr);

            if (transactionInfoDTO.getPaymentTermUpdate() != null) {
                paymentCycleDTOS.addAll(transactionInfoDTO.getPaymentTermUpdate());
            }
//            Cần một list invoice updated fỏ update to client
            if (transactionInfoDTO.getInvoiceInfo() != null) {
                invoiceInfos.add(transactionInfoDTO.getInvoiceInfo());
            }

            Transaction detailEntity = transactionMapper.toEntityFromDto(transactionInfoDTO);
            detailsToSave.add(detailEntity);
        });

        List<Transaction> savedDetails = transactionRepository.saveAll(detailsToSave);

        List<TransactionInfoDTO> transactionInfoDTOList = transactionMapper.toDTOList(savedDetails);

        PaymentOrderDTO createdDto = paymentOrderMapper.toDto(createdPaymentOrder);

        PaymentOrderWithDetail paymentOrderWithDetail = new PaymentOrderWithDetail();
        paymentOrderWithDetail.setPaymentOrderDTO(createdDto);
        paymentOrderWithDetail.setTransactionInfoDTOS(transactionInfoDTOList);
        paymentOrderWithDetail.setPaymentTermUpdates(paymentCycleDTOS);
        paymentOrderWithDetail.setInvoiceInfos(invoiceInfos);

        return paymentOrderWithDetail;
    }

    @Override
    public PaymentOrderWithDetail getPaymentOrderById(String paymentId) {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentOrder", "_id", paymentId));

        List<TransactionInfoDTO> paymentDetailList = transactionService.getPaymentDetailByPaymentOrderId(paymentId);
        PaymentOrderDTO paymentOrderDTO = paymentOrderMapper.toDto(paymentOrder);

        List<FileResponseDTO> fileResponseDTOS = paymentOrderDTO.getAttachments();

        List<String> fileNames = fileResponseDTOS.stream()
                .map(FileResponseDTO::getFileName)
                .toList();

        Map<String, String> urlMap = s3Service.getPresignedUrlsForFiles(fileNames, 10080L);

        fileResponseDTOS.forEach(file -> file.setFileUrl(urlMap.get(file.getFileName())));

        paymentOrderDTO.setAttachments(fileResponseDTOS);

        PaymentOrderWithDetail paymentOrderWithDetail = new PaymentOrderWithDetail();

        paymentOrderWithDetail.setPaymentOrderDTO(paymentOrderDTO);
        paymentOrderWithDetail.setTransactionInfoDTOS(paymentDetailList);

        return paymentOrderWithDetail;
    }

    @Override
    public PaymentOrderResponse getAllPaymentOrder(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<PaymentOrder> page = paymentOrderRepository.findAll(pageable);
        List<PaymentOrder> paymentOrderList = page.getContent();
        List<PaymentOrderDTO> paymentOrderDTOS = paymentOrderMapper.toDtoList(paymentOrderList);

        PaymentOrderResponse paymentOrderResponse = new PaymentOrderResponse();
        paymentOrderResponse.setContent(paymentOrderDTOS);
        paymentOrderResponse.setPageNumber(page.getNumber());
        paymentOrderResponse.setPageSize(page.getSize());
        paymentOrderResponse.setTotalElements(page.getTotalElements());
        paymentOrderResponse.setTotalPages(page.getTotalPages());
        paymentOrderResponse.setLastPage(page.isLast());

        return paymentOrderResponse;
    }

    @Override
    public PaymentOrderResponse getUnConfirmedPaymentOrder(String confirmStatus, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<PaymentOrder> page = paymentOrderRepository.getByIsConfirmed(confirmStatus, pageable);
        List<PaymentOrder> paymentOrderList = page.getContent();
        List<PaymentOrderDTO> paymentOrderDTOS = paymentOrderMapper.toDtoList(paymentOrderList);

        PaymentOrderResponse paymentOrderResponse = new PaymentOrderResponse();
        paymentOrderResponse.setContent(paymentOrderDTOS);
        paymentOrderResponse.setPageNumber(page.getNumber());
        paymentOrderResponse.setPageSize(page.getSize());
        paymentOrderResponse.setTotalElements(page.getTotalElements());
        paymentOrderResponse.setTotalPages(page.getTotalPages());
        paymentOrderResponse.setLastPage(page.isLast());

        return paymentOrderResponse;
    }

    @Override
    public PaymentOrderResponse getPaymentOrderByTenantId(String tenantId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public PaymentOrderDTO updatePaymentOrderById(String id, PaymentOrderPatchRequest paymentOrderPatchRequest) {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentOrder", "_id", id));

        paymentOrderMapper.updatePaymentOrderFromDtoList(paymentOrderPatchRequest, paymentOrder);
        PaymentOrder savedPaymentOrder = paymentOrderRepository.save(paymentOrder);

        return paymentOrderMapper.toDto(savedPaymentOrder);
    }

    @Override
    public String deletePaymentOrderById(String id) {
        paymentOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentOrder", "_id", id));

        List<Transaction> transactionList = transactionRepository.getByPaymentOrderId(id);

        transactionRepository.deleteAll(transactionList);
        paymentOrderRepository.deleteById(id);

        return "Delete PaymentOrder successfully!";
    }
}
