package com.example.demo01.domains.mongo.MiniCrm.Payment.service.impl;


import com.example.demo01.core.Basement.dto.basement.BUInfoDto;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixRequest;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerInfoDTO;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.dtos.Products.ProductInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionPatchRequest;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionRequest;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionResponse;
import com.example.demo01.utils.AppUtil;
import com.example.demo01.utils.FilterRequest;
import com.example.demo01.domains.mongo.MiniCrm.Contract.models.Appendix;
import com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository.AppendixRepository;
import com.example.demo01.domains.mongo.MiniCrm.Contract.service.PaymentCycleService;
import com.example.demo01.core.SpaceCustomer.service.CustomerInfoService;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.service.ProductService;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoiceInfo;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoiceRequest;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.service.InvoiceService;
import com.example.demo01.domains.mongo.MiniCrm.Payment.mapper.TransactionMapper;
import com.example.demo01.domains.mongo.MiniCrm.Payment.models.Transaction;
import com.example.demo01.repository.mongo.MiniCrmRepository.paymentRepository.TransactionRepository;
import com.example.demo01.domains.mongo.MiniCrm.Payment.service.CentralPaymentService;
import com.example.demo01.domains.mongo.MiniCrm.Payment.service.TransactionService;
import com.example.demo01.core.Exceptions.APIException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final CentralPaymentService centralPaymentService;

    private final MongoTemplate mongoTemplate;

    private final PaymentCycleService paymentCycleService;

    private final InvoiceService invoiceService;

    private final AppUtil appUtil;

    private final CustomerInfoService customerInfoService;

    private final BasementService basementService;

    private final ProductService productService;

    private final AppendixRepository appendixRepository;

    @Override
    public TransactionInfoDTO addPaymentDetail(TransactionRequest transactionRequest) {
        Double paidAmount = transactionRequest.getAmount();
        String customerIdValue = transactionRequest.getCustomerId();
        String contractIdValue = transactionRequest.getUsingId();
        String invoiceOwnerId;
        String buId = transactionRequest.getBuId();
        String productId = transactionRequest.getServiceId();
        InvoiceRequest invoiceInfo = transactionRequest.getInvoiceInfo();

        CustomerInfoDTO customerInfo = customerInfoService.findCustomerById(customerIdValue);

        BUInfoDto branchUnit = basementService.getBuInfoByShortName(buId);

        ProductInfoDto productInfoDto = productService.getProductById(productId);

        TransactionInfoDTO transactionInfoDTO = transactionMapper.fromRequestToDto(transactionRequest);

        if (!contractIdValue.isEmpty()) {
            Double maxAmount = paymentCycleService.remainingPaymentByAppendixId(contractIdValue);
            if (paidAmount > maxAmount) {
                throw new APIException("Paid amount must be lower than remain amount of contract: " + maxAmount.intValue());
            }
        } else {
            InvoiceInfo draftInvoice;
            invoiceOwnerId = transactionRequest.getInvoiceInfo().getInvoiceId();

            Double invoiceValue = invoiceInfo.getInvoiceValue();
            if ( invoiceValue < paidAmount) {
                throw new APIException("Paid amount must be lower than invoice total amount: " + invoiceValue.intValue());
            }
            if (invoiceOwnerId.isEmpty()) {
                draftInvoice = invoiceService.draftInvoice(transactionRequest, invoiceInfo);
            } else {
                draftInvoice = invoiceService.getInvoiceByInvoiceId(invoiceOwnerId);
                List<Transaction> existingPayments = transactionRepository.getByInvoiceId(invoiceOwnerId);
                Double existingPaidAmount = existingPayments.stream()
                        .mapToDouble(Transaction::getAmount)
                        .sum();
                double totalPaidAmount = existingPaidAmount + paidAmount;
                if (totalPaidAmount > draftInvoice.getInvoiceValue()) {
                    throw new APIException("Total paid amount " + (int) totalPaidAmount +
                            " exceeds invoice total amount " + draftInvoice.getInvoiceValue().intValue());
                }
            }
            transactionInfoDTO.setInvoiceId(invoiceOwnerId);
            transactionInfoDTO.setInvoiceInfo(draftInvoice);
        }


        transactionInfoDTO.setCustomerName(customerInfo.getCustomerTitle());
        transactionInfoDTO.setBuName(branchUnit.getBuFullName());
        transactionInfoDTO.setServiceName(productInfoDto.getServiceName());

        return transactionInfoDTO;
    }

//    @Transactional
    @Override
    public TransactionInfoDTO createAPaymentDetail(TransactionRequest transactionRequest) {
        TransactionInfoDTO handledItem = centralPaymentService.handlePayment(transactionRequest);
        Transaction savedItem = transactionRepository.save(transactionMapper.toEntityFromDto(handledItem));
        return transactionMapper.toDto(savedItem);
    }

    @Override
    public TransactionInfoDTO getPaymentDetailById(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentDetail", "_id", id));

        return transactionMapper.toDto(transaction);
    }

    @Override
    public List<TransactionInfoDTO> getTransactionsByPaymentId(String paymentId) {
        Appendix appendix = appendixRepository.findByAppendixCode(paymentId);
        if (appendix == null) {
            throw new ResourceNotFoundException("Appendix", "_id", paymentId);
        }
        List<Transaction> transactionList = transactionRepository.getByUsingId(paymentId);
        return transactionMapper.toDTOList(transactionList);
    }

    @Override
//    @Transactional
    public List<TransactionInfoDTO> getTransactionsForRefundByPaymentId(AppendixRequest appendixRequest, Boolean isReverse) {
        String oldItemId = appendixRequest.getUpdateAppendix();
        String targetId = appendixRequest.getAppendixCode();
        String newCustomerId = appendixRequest.getCustomerId();
        String targetServiceId = appendixRequest.getServiceId();
        String targetBuId = appendixRequest.getBuId();

        List<Transaction> transactions = transactionRepository.getByUsingId(oldItemId);
        if (isReverse) {
            transactions.sort(Comparator.comparing(Transaction::getPaymentDate).reversed());
        } else  {
            transactions.sort(Comparator.comparing(Transaction::getPaymentDate));
        }
        Double totalRefund = appendixRequest.getPaidAmount();


        List<Transaction> transactionToSave = new ArrayList<>();
        List<Transaction> deleteTransactions = new ArrayList<>();
        List<Transaction> newTransactions = new ArrayList<>();

        for (Transaction updateTransaction : transactions) {

            if (totalRefund <= 0) {
                break;
            }
            Transaction transactionRequest = new Transaction();

            double updateRefund = Math.min(updateTransaction.getAmount(), totalRefund);
            double updateLocalAmount = updateRefund * updateTransaction.getExchangeRate();
            double remainAmount = updateTransaction.getAmount() - updateRefund;

            transactionRequest.setAmount(updateRefund);
            transactionRequest.setExchangeRate(updateTransaction.getExchangeRate());
            transactionRequest.setUsingId(targetId);
            transactionRequest.setCustomerId(newCustomerId);
            transactionRequest.setPaymentDate(updateTransaction.getPaymentDate());
            transactionRequest.setServiceId(targetServiceId);
            transactionRequest.setPaymentOrderId(updateTransaction.getPaymentOrderId());
            transactionRequest.setDepositRemainAmount(updateRefund);
            transactionRequest.setLocalAmount(updateLocalAmount);
            transactionRequest.setPaymentType("PAYMENT");
            transactionRequest.setBuId(targetBuId);

            updateTransaction.setAmount(remainAmount);
            updateTransaction.setLocalAmount(remainAmount * updateTransaction.getExchangeRate());


            newTransactions.add(transactionRequest);
            if (remainAmount <= 0) {
                deleteTransactions.add(updateTransaction);
            } else {
                transactionToSave.add(updateTransaction);
            }
            totalRefund -= updateRefund;
        }
        if (!transactionToSave.isEmpty()) {
            transactionRepository.saveAll(transactionToSave);
        }
        if (!deleteTransactions.isEmpty()) {
            transactionRepository.deleteAll(deleteTransactions);
        }

        List<Transaction> savedTransactions = transactionRepository.saveAll(newTransactions);

        return transactionMapper.toDTOList(savedTransactions);
    }

    @Override
    public List<TransactionInfoDTO> getPaymentDetailByPaymentOrderId(String paymentOrderId) {
        List<Transaction> transactionList = transactionRepository.getByPaymentOrderId(paymentOrderId);
        if (transactionList == null) {
            throw new ResourceNotFoundException("PaymentDetail", "paymentOrderId", paymentOrderId);
        }
        return transactionMapper.toDTOList(transactionList);
    }

    @Override
    public BasePageResponse<TransactionInfoDTO> getAllPaymentDetail(FilterWithPagination filterWithPagination) {
        Pageable pageDetails = filterWithPagination.getPagination().toPageable();
        List<FilterRequest> filterRequests = filterWithPagination.getFilters();
        return getTransactionWithFilter(pageDetails, filterRequests);
    }

    @Override
    public BasePageResponse<TransactionInfoDTO> getTransactionWithFilter(Pageable pageable, List<FilterRequest> filterRequests) {
        Sort sort = pageable.getSort();
        List<Criteria> finalCriteriaList = new ArrayList<>();
        Query query = appUtil.applyFilter(filterRequests, finalCriteriaList).with(pageable).with(sort);

        List<Transaction> list = mongoTemplate.find(query, Transaction.class);

        long total = mongoTemplate.count(query.skip(-1).limit(-1), Transaction.class);

        PageImpl<Transaction> page = new PageImpl<>(list, pageable, total);

        return buildPageResponse(page);
    }

    @Override
    public List<TransactionInfoDTO> getPaymentDetailByCustomerId(String customerId) {
        List<Transaction> transactionList = transactionRepository.getByCustomerId(customerId);
        return transactionMapper.toDTOList(transactionList);
    }

    @NonNull
    private TransactionResponse getPaymentDetailResponse(Page<Transaction> page, List<TransactionInfoDTO> transactionInfoDTOS) {
        TransactionResponse transactionResponse = new TransactionResponse();

        transactionResponse.setContent(transactionInfoDTOS);
        transactionResponse.setPageNumber(page.getNumber());
        transactionResponse.setPageSize(page.getSize());
        transactionResponse.setTotalElements(page.getTotalElements());
        transactionResponse.setTotalPages(page.getTotalPages());
        transactionResponse.setLastPage(page.isLast());
        return transactionResponse;
    }

    @Override
    public TransactionInfoDTO updatePaymentDetail(String id, TransactionPatchRequest transactionPatchRequest) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentDetail", "_id", id));
        transactionMapper.updatePaymentFromDto(transactionPatchRequest, transaction);
        Transaction updatedPayment = transactionRepository.save(transaction);

        return transactionMapper.toDto(updatedPayment);
    }

    @Override
    public List<TransactionInfoDTO> deleteTransactionByPaymentId(String paymentId) {
        List<Transaction> transactionList = transactionRepository.getByUsingId(paymentId);
        if (transactionList.isEmpty()) return null;

        transactionRepository.deleteAll(transactionList);

        return transactionMapper.toDTOList(transactionList);
    }

    @Override
    public List<TransactionInfoDTO> getTransactionByInvoiceId(String invoiceId) {
        List<Transaction> transactionList = transactionRepository.getByInvoiceId(invoiceId);
        if (transactionList.isEmpty()) {
            throw new ResourceNotFoundException("PaymentDetail", "invoiceId", invoiceId);
        }
        return transactionMapper.toDTOList(transactionList);
    }

    @Override
    public String deletePaymentDetail(String id) {
       transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentDetail", "_id", id));
        transactionRepository.deleteById(id);

        return "Deleted paymentDetail successfully!";
    }

    public BasePageResponse<TransactionInfoDTO> buildPageResponse(@NonNull Page<Transaction> page) {
        List<TransactionInfoDTO> transactionInfoDTOS = transactionMapper.toDTOList(page.getContent());

        List<String> customerIds = transactionInfoDTOS.stream()
                .map(TransactionInfoDTO::getCustomerId)
                .distinct()
                .toList();

        List<String> buShortNames = transactionInfoDTOS.stream()
                .map(TransactionInfoDTO::getBuId)
                .distinct()
                .toList();

        List<String> serviceIds = transactionInfoDTOS.stream()
                .map(TransactionInfoDTO::getServiceId)
                .distinct()
                .toList();

        Map<String, Object> customerNames = customerInfoService.getBatchCustomerTitle(customerIds);
        Map<String, Object> buFullNames = basementService.getBatchBuFullNames(buShortNames);
        Map<String, Object> serviceNames = productService.getBatchByServiceName(serviceIds);

        List<TransactionInfoDTO> handedArr = transactionInfoDTOS.stream().peek(it -> {
            String customerName = (String) customerNames.get(it.getCustomerId());
            String buFullName = (String) buFullNames.get(it.getBuId());
            String serviceName = (String) serviceNames.get(it.getServiceId());

            it.setServiceName(serviceName);
            it.setBuName(buFullName);
            it.setCustomerName(customerName);
        }).toList();


        BasePageResponse<TransactionInfoDTO> response = new BasePageResponse<>();
        response.setContent(handedArr);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }

}
