package com.example.demo01.domains.MiniCrm.Invoice.service;

import com.example.demo01.core.AuditUpdate.Dto.AuditUpdateDto;
import com.example.demo01.core.AuditUpdate.model.ChangeType;
import com.example.demo01.core.AuditUpdate.service.AuditItemService;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCyclePatchRequestDto;
import com.example.demo01.domains.MiniCrm.Contract.service.PaymentCycleService;
import com.example.demo01.core.Exceptions.APIException;
import com.example.demo01.domains.MiniCrm.Invoice.dto.*;
import com.example.demo01.domains.MiniCrm.Utils.MC_Utils;
import com.example.demo01.utils.AppUtil;
import com.example.demo01.utils.FilterRequest;
import com.example.demo01.core.SpaceCustomer.service.CustomerInfoService;
import com.example.demo01.domains.MiniCrm.Dimmesion.service.ProductService;
import com.example.demo01.domains.MiniCrm.Invoice.mapper.InvoiceMapper;
import com.example.demo01.domains.MiniCrm.Invoice.model.Invoice;
import com.example.demo01.domains.MiniCrm.Invoice.model.InvoiceStatus;
import com.example.demo01.domains.MiniCrm.Invoice.repository.InvoiceRepository;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.TransactionRequest;
import com.example.demo01.domains.MiniCrm.Payment.models.Transaction;
import com.example.demo01.domains.MiniCrm.Payment.repository.TransactionRepository;
import com.example.demo01.domains.MiniCrm.ProfitAndLost.dtos.AllocationObject;
import com.example.demo01.domains.MiniCrm.ProfitAndLost.service.ProfitAndLossService;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.PageInput;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    private final MongoTemplate mongoTemplate;

    private final MC_Utils mcUtils;

    private final AppUtil appUtil;

    private final ProfitAndLossService profitAndLossService;

    private final AuditItemService auditItemService;

    private final TransactionRepository transactionRepository;

    private final PaymentCycleService paymentCycleService;

    private final CustomerInfoService customerInfoService;

    private final ProductService productService;

    private final BasementService basementService;

    @Override
    public InvoiceInfo createInvoiceByPo(InvoiceRequest request) {
        return createInvoiceByRequest(request);
    }

    @Override
    public InvoiceInfo createInvoiceByPaymentTerm(PaymentCycleDTO paymentCycleDTO) {

        InvoiceRequest request = new InvoiceRequest();
        request.setServiceId(paymentCycleDTO.getServiceId());
        request.setBuId(paymentCycleDTO.getBuId());
        request.setCustomerId(paymentCycleDTO.getCustomerId());
        request.setAppendixCode(paymentCycleDTO.getAppendixId());
        request.setInvoiceValue(paymentCycleDTO.getTotalAmount());
        request.setContractId(paymentCycleDTO.getContractId());
        request.setInvoiceDate(paymentCycleDTO.getCycleDueDate());
        request.setInvoiceTag("revenue");
        request.setCycleId(paymentCycleDTO.getCycleId());

        PaymentCyclePatchRequestDto patchRequestDto = new PaymentCyclePatchRequestDto();
        patchRequestDto.setExportedInvoice(true);

        paymentCycleService.updatePaymentCycle(paymentCycleDTO.get_id(), patchRequestDto);

        return createInvoiceByRequest(request);
    }

    @Override
    public InvoiceInfo createInvoiceByRequest(InvoiceRequest invoiceRequest) {
        String invoiceIdGen = mcUtils.invoiceCodeGen(invoiceRequest.getServiceId(), invoiceRequest.getBuId(),invoiceRequest.getInvoiceValue(),invoiceRequest.getCustomerId());
        invoiceRequest.setInvoiceId(invoiceIdGen);
        invoiceRequest.setActive(true);
        invoiceRequest.setExportStatus(InvoiceStatus.INITIAL);
        Invoice newItem = invoiceRepository.save(invoiceMapper.toEntity(invoiceRequest));
        return invoiceMapper.toDto(newItem);
    }

    @Override
    public InvoiceInfo updateInvoiceByPo(String invoiceId, TransactionRequest paymentRequest) {
        InvoiceInfo invoiceInfo = getInvoiceByInvoiceId(invoiceId);
        Boolean isCompleted = invoiceInfo.getActive();
        if (isCompleted) {
            Double updateLocalAmount = paymentRequest.getAmount() * paymentRequest.getExchangeRate();
            Double originAmount = paymentRequest.getAmount();
            AllocationObject allocationObject = getAllocationObject(originAmount, updateLocalAmount,invoiceInfo );
            profitAndLossService.updateExistItem(allocationObject, invoiceInfo.getInvoiceId());
        }
        return null;
    }

    @Override
    public InvoiceInfo invoiceByPo(TransactionRequest paymentRequest) {
        String invoiceId = paymentRequest.getInvoiceId();
        InvoiceRequest invoiceInfoResult = paymentRequest.getInvoiceInfo();
        if (invoiceId.isEmpty()) {
            if (invoiceInfoResult.getInvoiceDate() == null) {
                invoiceInfoResult.setInvoiceDate(paymentRequest.getPaymentDate());
            }
            return createInvoiceByPo(invoiceInfoResult);
        } else {
            return updateInvoiceByPo(invoiceId, paymentRequest);
        }
    }

    @Override
    public InvoiceInfo draftInvoice(TransactionRequest transactionRequest, @UnknownNullability InvoiceRequest invoiceInfo) {
        invoiceInfo.setExportStatus(InvoiceStatus.INITIAL);
        boolean isFullyPaid = transactionRequest.getAmount() >= invoiceInfo.getInvoiceValue();
        invoiceInfo.setIsPaid(isFullyPaid);
        return invoiceMapper.toDtoFromRequest(invoiceInfo);
    }

    @Override
    public InvoiceInfo getInvoiceByInvoiceId(String invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() ->  new ResourceNotFoundException("Invoice","_id",invoiceId));
        return invoiceMapper.toDto(invoice);
    }

    @Override
    public InvoiceInfo updateInvoice(String invoiceId, InvoicePatchRequest patchRequest) {
        Invoice invoiceItem = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice","_id",invoiceId));

        InvoiceStatus currentStatus = invoiceItem.getExportStatus();
        InvoiceStatus updatedStatus = patchRequest.getExportStatus();
        invoiceMapper.updateInvoiceFromDto(patchRequest, invoiceItem);
        if (canTransition(currentStatus, updatedStatus) || currentStatus == updatedStatus) {
            Double  paidValue = getTotalPaidAmountAggregation(invoiceId);
            Invoice updatedItem = invoiceRepository.save(invoiceItem);
            InvoiceInfo invoiceInfo = invoiceMapper.toDto(updatedItem);

            AuditUpdateDto auditUpdateDto = new AuditUpdateDto();
            auditUpdateDto.setChangeType(ChangeType.UPDATE);
            auditUpdateDto.setDescription(patchRequest.getUpdateDescription());
            auditUpdateDto.setEntityId(invoiceId);

            auditItemService.createAuditItem(auditUpdateDto);

            if (!updatedItem.getActive()) {
                List<Transaction> transactions = transactionRepository.getByInvoiceId(invoiceId);
                double totalPaidFromTransactions;
                if (!transactions.isEmpty()) {
                    totalPaidFromTransactions = transactions.stream()
                            .map(t -> t.getAmount() * t.getExchangeRate())
                            .reduce(0.0, Double::sum);
                } else {
                    totalPaidFromTransactions = 0.0;
                }
                AllocationObject allocationObject = getAllocationObject(paidValue ,totalPaidFromTransactions, invoiceInfo);
                profitAndLossService.handleAllocate(allocationObject);
            }
            return invoiceInfo;
        } else  {
            throw new APIException("Invalid status transition from " + currentStatus + " to " + updatedStatus);
        }
    }

    @Override
    public BasePageResponse<InvoiceInfo> getProcessInvoice(Boolean active, List<FilterRequest> filterRequest, PageInput pageInput) {
        Pageable pageable = pageInput.toPageable();
        List<Criteria> criteriaList = new ArrayList<>();
        Criteria activeCriteria = Criteria.where("active").is(active);
        criteriaList.add(activeCriteria);
        return searchInvoice(criteriaList, filterRequest, pageable);
    }

    @Override
    public BasePageResponse<InvoiceInfo> searchInvoice(List<Criteria> criteriaList, List<FilterRequest> filterRequest, Pageable pageable) {
        Query query = appUtil.applyFilter(filterRequest, criteriaList).with(pageable);

        List<Invoice> invoiceList = mongoTemplate.find(query, Invoice.class);

        long total = mongoTemplate.count(query, Invoice.class);

        PageImpl<Invoice> invoicePage = new PageImpl<>(invoiceList, pageable, total);
        return buildPageResponse(invoicePage);
    }


    private static @NonNull AllocationObject getAllocationObject(Double paidAmount , Double localAmount, InvoiceInfo updatedItem) {
        AllocationObject allocateItem = new AllocationObject();
        allocateItem.setTotalPaidAmount(paidAmount);
        allocateItem.setAllocationPerLoop(updatedItem.getInvoiceValue());
        allocateItem.setAllocationTotalAmount(localAmount);
        allocateItem.setExchangeRate(1.0);
        allocateItem.setBeginAllocationDate(updatedItem.getInvoiceDate());
        allocateItem.setInvoiceId(updatedItem.getInvoiceId());
        allocateItem.setServiceId(updatedItem.getServiceId());
        allocateItem.setBuId(updatedItem.getBuId());
        return allocateItem;
    }

    public Double getTotalPaidAmountAggregation(String invoiceId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(invoiceId)),

                Aggregation.unwind("paymentOrders"),

                Aggregation.group()
                        .sum(ArithmeticOperators.Multiply.valueOf("paymentOrders.paidAmount")
                                .multiplyBy("paymentOrders.exchangeRate"))
                        .as("totalValue"),
                Aggregation.project("totalValue")
        );

        AggregationResults<Document> result = mongoTemplate.aggregate(aggregation, Invoice.class, Document.class);
        Document uniqueResult = result.getUniqueMappedResult();

        if (uniqueResult != null && uniqueResult.get("totalValue") != null) {
            return uniqueResult.getDouble("totalValue");
        }
        return 0.0;
    }

    @Override
    public InvoiceResponse getAllInvoice(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public List<InvoiceInfo> getActiveInvoiceByCustomerId(String customerId) {
        List<Invoice> invoiceList = invoiceRepository.getActiveInvoicesByCustomerId(customerId);
        return invoiceMapper.toDtoList(invoiceList);
    }

    private BasePageResponse<InvoiceInfo> buildPageResponse(Page<Invoice> page) {

        List<InvoiceInfo> invoiceList = invoiceMapper.toDtoList(page.getContent());

        List<String> customerIds = invoiceList.stream()
                .map(InvoiceInfo::getCustomerId)
                .distinct()
                .toList();

        List<String> buShortNames = invoiceList.stream()
                .map(InvoiceInfo::getBuId)
                .distinct()
                .toList();

        List<String> serviceIds = invoiceList.stream()
                .map(InvoiceInfo::getServiceId)
                .distinct()
                .toList();

        Map<String, Object> customerNames = customerInfoService.getBatchCustomerTitle(customerIds);
        Map<String, Object> buFullNames = basementService.getBatchBuFullNames(buShortNames);
        Map<String, Object> serviceNames = productService.getBatchByServiceName(serviceIds);

        for (InvoiceInfo dto : invoiceList) {
            String customerName = (String) customerNames.getOrDefault(dto.getCustomerId(), "Unknown Customer");
            String buName = (String) buFullNames.getOrDefault(dto.getBuId(), "Unknown Bu");
            String serviceNameValue = (String) serviceNames.getOrDefault(dto.getServiceId(), "Unknown Service");

            dto.setCustomerName(customerName);
            dto.setBuName(buName);
            dto.setProductName(serviceNameValue);
        }
        return new BasePageResponse<>(
                invoiceList,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    private static final Map<InvoiceStatus, List<InvoiceStatus>> ALLOWED_TRANSITIONS = new EnumMap<>(InvoiceStatus.class);

    static {
        ALLOWED_TRANSITIONS.put(InvoiceStatus.INITIAL, List.of(InvoiceStatus.DRAFT, InvoiceStatus.CANCELED, InvoiceStatus.EXPORTED, InvoiceStatus.PENDING));
        ALLOWED_TRANSITIONS.put(InvoiceStatus.PENDING, List.of(InvoiceStatus.INITIAL, InvoiceStatus.DRAFT, InvoiceStatus.CANCELED));
        ALLOWED_TRANSITIONS.put(InvoiceStatus.CONFIRMED, List.of(InvoiceStatus.EXPORTED, InvoiceStatus.CANCELED));
        ALLOWED_TRANSITIONS.put(InvoiceStatus.DRAFT, List.of(InvoiceStatus.CONFIRMED, InvoiceStatus.PENDING));
        ALLOWED_TRANSITIONS.put(InvoiceStatus.CANCELED, List.of(InvoiceStatus.INITIAL));
        ALLOWED_TRANSITIONS.put(InvoiceStatus.EXPORTED, Collections.emptyList());
    }

    private boolean canTransition(InvoiceStatus currentStatus, InvoiceStatus newStatus) {
        if (currentStatus == null || newStatus == null) return false;
        return ALLOWED_TRANSITIONS.get(currentStatus).contains(newStatus);
    }

    @Override
    public List<InvoiceActionResponse> getAvailableActions(InvoiceStatus currentStatus) {
        if (currentStatus == null) {
            throw new APIException("Current status cannot be null");
        }
        List<InvoiceStatus> nextStatuses = ALLOWED_TRANSITIONS.getOrDefault(currentStatus, Collections.emptyList());
        return nextStatuses.stream()
                .map(status -> new InvoiceActionResponse(
                        status.name(),
                        getActionLabel(status),
                        getActionType(status)
                ))
                .collect(Collectors.toList());
    }


    private String getActionLabel(InvoiceStatus status) {
        return switch (status) {
            case INITIAL -> "Khôi phục trạng thái ban đầu";
            case DRAFT -> "Gửi nháp";
            case CANCELED -> "Hủy hóa đơn";
            case EXPORTED -> "Xuất hóa đơn";
            case PENDING -> "Yêu cầu chỉnh sửa";
            case CONFIRMED -> "Xác nhận";
        };
    }

    // Mapping để Frontend quyết định style (Primary, Danger, v.v.)
    private String getActionType(InvoiceStatus status) {
        return switch (status) {
            case CANCELED -> "DANGER";
            case EXPORTED, CONFIRMED -> "POSITIVE";
            case INITIAL -> "WARNING";
            default -> "DEFAULT";
        };
    }

}
