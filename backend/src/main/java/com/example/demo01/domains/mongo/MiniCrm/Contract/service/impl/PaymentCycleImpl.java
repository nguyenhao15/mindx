package com.example.demo01.domains.mongo.MiniCrm.Contract.service.impl;

import com.example.demo01.core.Basement.model.BranchUnit;
import com.example.demo01.repository.mongo.CoreRepo.BasementRepository.BranchUnitRepository;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.utils.FilterRequest;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleBulkGen;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCyclePatchRequestDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.mappers.PaymentCycleMapper;
import com.example.demo01.domains.mongo.MiniCrm.Contract.models.Appendix;
import com.example.demo01.domains.mongo.MiniCrm.Contract.models.PaymentCycle;
import com.example.demo01.domains.mongo.MiniCrm.Contract.models.PaymentTermStatus;
import com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository.AppendixRepository;
import com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository.PaymentCycleRepository;
import com.example.demo01.domains.mongo.MiniCrm.Contract.service.ContractUtilService;
import com.example.demo01.domains.mongo.MiniCrm.Contract.service.PaymentCycleService;
import com.example.demo01.core.SpaceCustomer.models.CustomerInfo;
import com.example.demo01.repository.mongo.CoreRepo.SpaceCustomerRepository.CustomerInfoRepository;
import com.example.demo01.core.SpaceCustomer.service.CustomerInfoService;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.model.ProductModel;
import com.example.demo01.repository.mongo.MiniCrmRepository.dimRepository.ProductRepository;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.service.ProductService;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.UpdatePaymentTermDetail;
import com.example.demo01.domains.mongo.MiniCrm.Payment.models.TransactionAllocate;
import com.example.demo01.domains.mongo.MiniCrm.ProfitAndLost.dtos.AllocationObject;
import com.example.demo01.domains.mongo.MiniCrm.ProfitAndLost.service.ProfitAndLossService;
import com.example.demo01.core.Exceptions.APIException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.utils.AppUtil;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.PageInput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PaymentCycleImpl implements PaymentCycleService {

    private final AppUtil appUtil;

    private final PaymentCycleRepository paymentCycleRepository;

    private final PaymentCycleMapper paymentCycleMapper;

    private final ProductRepository productRepository;

    private final BranchUnitRepository branchUnitRepository;

    private final ProfitAndLossService profitAndLossService;

    private final CustomerInfoRepository customerInfoRepository;

    private final ContractUtilService contractUtilService;

    private final AppendixRepository appendixRepository;

    private final MongoTemplate mongoTemplate;

    private final CustomerInfoService customerInfoService;

    private final ProductService productService;

    private final BasementService basementService;

//    @Transactional
    @Override
    public List<PaymentCycleDTO> handleGeneratePaymentCyclePreview(PaymentCycleBulkGen paymentCycleBulkGen) {
        int extraMonth = (
                paymentCycleBulkGen.getEndDate().getDayOfMonth() >= paymentCycleBulkGen.getStartDate().getDayOfMonth() ||
                        paymentCycleBulkGen.getEndDate().getDayOfMonth() == paymentCycleBulkGen.getEndDate().lengthOfMonth()
        ) ? 1 : 0;

        Double paidAmountValue = paymentCycleBulkGen.getPaidAmount();
        Double contractValue = paymentCycleBulkGen.getTotalValue();
        Boolean isManualMode = paymentCycleBulkGen.getIsManualMode();
        LocalDate beginDate = paymentCycleBulkGen.getStartDate();
        LocalDate doneDate = paymentCycleBulkGen.getEndDate();
        Double monthlyAmount = paymentCycleBulkGen.getValuePerMonth();
        String buShortName = paymentCycleBulkGen.getBuId();
        String customerId = paymentCycleBulkGen.getCustomerId();
        String contractId = paymentCycleBulkGen.getContractCode();
        String serviceId = paymentCycleBulkGen.getServiceId();
        String currencyId = paymentCycleBulkGen.getCurrencyId();

        boolean paidCheck = paidAmountValue > 0;
        double totalPaid = 0;
        double paidValue = 0;
        PaymentTermStatus cycleStatus = PaymentTermStatus.INITIAL;


        List<PaymentCycle> paymentCycleList = new ArrayList<>();

        int paymentTermValue = paymentCycleBulkGen.getPaymentTerm();
        double usingRateAtFirstMonth = (double) (beginDate.lengthOfMonth() - beginDate.minusDays(1).getDayOfMonth()) / beginDate.lengthOfMonth();

        int MonthDuration = (int) ChronoUnit.MONTHS.between(
                paymentCycleBulkGen.getStartDate(),
                paymentCycleBulkGen.getEndDate().with(TemporalAdjusters.lastDayOfMonth())
        ) + extraMonth;

        int loopValue =  isManualMode ? 1 : (int) Math.ceil((double) MonthDuration / paymentCycleBulkGen.getPaymentTerm());

        DateTimeFormatter customFormater = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate previousCycleEnd = null;
        double allocatedAmount = 0;
        String trackTitle;
        boolean isFirst;
        boolean isLast;


        LocalDate cycleStart;
        LocalDate cycleEnd;

        boolean after15th = beginDate.getDayOfMonth() >= 15;
        for (int i = 1; i <= loopValue; i++) {
            isFirst = i == 1;
            cycleStart = isFirst
                    ? beginDate
                    : previousCycleEnd.plusDays(1);
            if (cycleStart.isAfter(doneDate)) {
                break;
            }

            if (isManualMode) {
                cycleEnd = doneDate;
            } else if (isFirst) {
                LocalDate handleEndDate = after15th
                        ? beginDate.plusMonths(paymentTermValue).with(TemporalAdjusters.lastDayOfMonth())
                        : beginDate.plusMonths(paymentTermValue);
                cycleEnd = doneDate.isBefore(handleEndDate) ? doneDate : handleEndDate;
            } else {
                LocalDate expectedDate = cycleStart.plusMonths(paymentTermValue).minusDays(1);
                cycleEnd = doneDate.isBefore(expectedDate) ? doneDate : expectedDate;
            }

            isLast = i == loopValue || cycleEnd.isEqual(doneDate);
            int usingDays = Math.toIntExact(ChronoUnit.DAYS.between(cycleStart.minusDays(1), cycleEnd));
            double handlePaymentAmount = isLast
                    ? contractValue - allocatedAmount
                    : (isFirst && after15th)
                    ? monthlyAmount * (paymentTermValue + usingRateAtFirstMonth)
                    : monthlyAmount * paymentTermValue;
            double paymentAmount = (int) Math.round(handlePaymentAmount);
            String myGuid = appUtil.handleSubString(UUID.randomUUID().toString(), 3, false);
            trackTitle = String.format("%s-%s-%s-%s-%s-%s",
                    buShortName,
                    cycleStart.format(customFormater),
                    usingDays,
                    appUtil.handleSubString(customerId, 3, false),
                    appUtil.handleSubString(contractId, 4, false),
                    myGuid
            );


            if (paidCheck) {
                paidValue = Math.min(
                        paidAmountValue - totalPaid,
                        paymentAmount
                );
                cycleStatus = paidValue == paymentAmount ? PaymentTermStatus.PAID : PaymentTermStatus.WAITING;

            }
            double remainAmountUpdate = paymentAmount - paidValue;

            PaymentCycle paymentCycleThisLoop = new PaymentCycle();
            paymentCycleThisLoop.setServiceId(serviceId);
            paymentCycleThisLoop.setCycleId(trackTitle);
            paymentCycleThisLoop.setPaidAmount(paidValue);
            paymentCycleThisLoop.setMonthlyAmount(monthlyAmount);
            paymentCycleThisLoop.setBuId(buShortName);
            paymentCycleThisLoop.setContractId(contractId);
            paymentCycleThisLoop.setCycleDueDate(cycleStart);
            paymentCycleThisLoop.setDays(usingDays);
            paymentCycleThisLoop.setMonthlyAmount(monthlyAmount);
            paymentCycleThisLoop.setActive(false);
            paymentCycleThisLoop.setTotalAmount(paymentAmount);
            paymentCycleThisLoop.setExportedInvoice(false);
            paymentCycleThisLoop.setCurrencyId(customerId);
            paymentCycleThisLoop.setStatus(cycleStatus);
            paymentCycleThisLoop.setCurrencyId(currencyId);
            paymentCycleThisLoop.setRemainAmount(remainAmountUpdate);

            allocatedAmount += paymentAmount;
            previousCycleEnd = cycleEnd;

            paymentCycleList.add(paymentCycleThisLoop);

            if (cycleEnd.isEqual(doneDate)) {
                break;
            }
        }

        return paymentCycleMapper.toDTOList(paymentCycleList);
    }

    @Override
    public List<PaymentCycleDTO> createPaymentCycles(List<PaymentCycleDTO> paymentCycleDTOList) {
        List<PaymentCycle> paymentCycles = paymentCycleRepository.saveAll(paymentCycleMapper.toEntityList(paymentCycleDTOList));
        return paymentCycleMapper.toDTOList(paymentCycles);
    }

    @Override
    public List<PaymentCycleDTO> getPaymentCycleByAppendixId(String appendixId) {
        List<PaymentCycle> paymentCycles = paymentCycleRepository.getByAppendixId(appendixId);
        if (paymentCycles.isEmpty()) {
            throw new APIException("Cant find any payment with appendixId is: " + appendixId);
        }
        return paymentCycleMapper.toDTOList(paymentCycles);
    }

    @Override
//    @Transactional(rollbackOn =  Exception.class)
    public UpdatePaymentTermDetail handleAddPayment(String appendixId, Double addedAmount, Double exchangeRate) {

        if (addedAmount == null || addedAmount <= 0) throw new  APIException("Add amount must be greater than zero");

        Appendix appendix = appendixRepository.findByAppendixCode(appendixId);

        if (appendix == null) {
            throw  new ResourceNotFoundException("Appendix", "appendixCode", appendixId);
        }

        List<PaymentCycle> paymentCycleList = paymentCycleRepository.getByAppendixIdAndActive(appendixId, true);

        if (paymentCycleList.isEmpty()) {
            throw new APIException("Cant find any payment term active with appendix is: " + appendixId);
        }

        List<TransactionAllocate> transactionAllocates = new ArrayList<>();

        double paidAmountValue = addedAmount;
        List<PaymentCycle> updateCycles = new ArrayList<>();
        paymentCycleList.sort(Comparator.comparing(PaymentCycle::getCycleDueDate));

        for (PaymentCycle item : paymentCycleList) {

            double remainThisItem = item.getTotalAmount() - item.getPaidAmount();
            double totalAmount = item.getTotalAmount();
            double updatePaidValueThisLoop = Math.min(remainThisItem, paidAmountValue);
            if (updatePaidValueThisLoop <= 0) {
                break;
            }


            double newPaidValue = updatePaidValueThisLoop + item.getPaidAmount();
            double newRemainValue = totalAmount - newPaidValue;
            boolean isDone = (totalAmount - newPaidValue) < 1;

            item.setPaidAmount(newPaidValue);
            item.setActive(!isDone);
            item.setRemainAmount(newRemainValue);

            if (isDone) {
                item.setStatus(PaymentTermStatus.PAID);
            } else {
                item.setStatus(PaymentTermStatus.WAITING);
            }

            TransactionAllocate transactionAllocate = new TransactionAllocate();
            transactionAllocate.setAllocateAmount(updatePaidValueThisLoop);
            transactionAllocate.setIdentifier(item.get_id());
            transactionAllocate.setAllocateItem("PaymentCycle");
            transactionAllocates.add(transactionAllocate);


            updateCycles.add(item);
            AllocationObject allocationObject = profitAndLossService.getAllocationObject(updatePaidValueThisLoop, exchangeRate, paymentCycleMapper.toDTO(item));
            profitAndLossService.handleAllocate(allocationObject);

            paidAmountValue -= updatePaidValueThisLoop;
            if (paidAmountValue == 0) {
                break;
            }
        }

        if (updateCycles.isEmpty()) {
            throw new APIException("All payment terms are completed, cannot add more payment.");
        }

        List<PaymentCycle> paymentCycles = paymentCycleRepository.saveAll(updateCycles);

        return new UpdatePaymentTermDetail(
                paymentCycleMapper.toDTOList(paymentCycles),
                transactionAllocates
        );
    }

    @Override
    public List<PaymentCycleDTO> refundPaymentHandle(String appendixId, List<TransactionInfoDTO> refundTransactions) {
        List<PaymentCycleDTO> paymentCycles = getPaymentCycleByAppendixId(appendixId);
        paymentCycles.sort(
                Comparator.comparing(PaymentCycleDTO::getCycleDueDate).reversed()
        );

        List<PaymentCycleDTO> havePaymentAmountItem = paymentCycles.stream()
                .filter(item -> item.getPaidAmount() > 0)
                .toList();

        for (TransactionInfoDTO item : refundTransactions) {
            Double refundAmount = item.getAmount();
            Double exchangeRate = item.getExchangeRate();
            processRefundAmount(havePaymentAmountItem, refundAmount, exchangeRate);
        }

        return List.of();
    }

    @Override
    public List<PaymentCycleDTO> handlePaymentWithListTransaction(String appendixId, List<TransactionInfoDTO> transactions) {

        return List.of();
    }

    private void processRefundAmount(List<PaymentCycleDTO> havePaymentAmountItem, Double refundAmount, Double exchangeRate) {
        for (PaymentCycleDTO paymentCycle : havePaymentAmountItem) {
            double paidAmountThisItem = paymentCycle.getPaidAmount();
            double refundValueThisLoop = Math.min(paidAmountThisItem, refundAmount);
            if (refundValueThisLoop <= 0) {
                break;
            }

            double newPaidValue = paidAmountThisItem - refundValueThisLoop;
            boolean isDone = (paymentCycle.getTotalAmount() - newPaidValue) < 1;

            paymentCycle.setPaidAmount(newPaidValue);
            paymentCycle.setActive(!isDone);
            paymentCycle.setRemainAmount(paymentCycle.getTotalAmount() - newPaidValue);

            AllocationObject allocationObject = profitAndLossService.getAllocationObject(refundValueThisLoop, exchangeRate, paymentCycle);
            profitAndLossService.handleDeallocate(allocationObject, paymentCycle.getCycleId());

            refundAmount -= refundValueThisLoop;
            if (refundAmount == 0) {
                break;
            }
        }
    }


    @Override
    public BasePageResponse<PaymentCycleDTO> getAllPaymentCycle(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<PaymentCycle> page = paymentCycleRepository.findAll(pageDetails);

        return buildPageResponse(page);
    }

    @Override
    public BasePageResponse<PaymentCycleDTO> getActivePaymentCycle(Boolean done, PageInput pageInput, List<FilterRequest> filterRequest) {
        Pageable pageDetails = pageInput.toPageable();
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("active").is(done));
        return searchPaymentCycles(filterRequest, pageDetails, criteriaList);
    }

    @Override
    public BasePageResponse<PaymentCycleDTO> getPaymentCycleForInvoiceProcess(Boolean done, List<FilterRequest> filterRequest, PageInput pageInput) {
        Pageable pageDetails = pageInput.toPageable();
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("exportedInvoice").is(done));
        return searchPaymentCycles(filterRequest, pageDetails, criteriaList);
    }

    @Override
    public BasePageResponse<PaymentCycleDTO> searchPaymentCycles(List<FilterRequest> filterRequest, Pageable pageable, List<Criteria> criteriaList) {
        List<Criteria> finalCriteriaList = new ArrayList<>();

        if (!criteriaList.isEmpty()) {
              finalCriteriaList.addAll(criteriaList);
        }

        Query query = appUtil.applyFilter(filterRequest, finalCriteriaList).with(pageable);

        List<PaymentCycle> list = mongoTemplate.find(query, PaymentCycle.class);

        long total = mongoTemplate.count(query.skip(-1).limit(-1), PaymentCycle.class);

        PageImpl<PaymentCycle> page = new PageImpl<>(list, pageable, total);

        return buildPageResponse(page);
    }

    @Override
    public BasePageResponse<PaymentCycleDTO> getPaymentCycleByBu(String buShortName, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<PaymentCycle> page = paymentCycleRepository.getByBuId(buShortName, pageDetails);


        return buildPageResponse(page);
    }

    @Override
    public List<PaymentCycleDTO> updateActivePaymentCyclesByAppendixId(String appendixId, Boolean active, String updateType) {
        List<PaymentCycle> paymentCycles = paymentCycleRepository.getByAppendixIdAndActive(appendixId, !active);
        if (paymentCycles.isEmpty()) {
            return List.of();
        } else {
            PaymentTermStatus updateTypeValue = switch (updateType) {
                case "MARK_AS_ACTIVE" -> PaymentTermStatus.WAITING;
                case "MARK_AS_COMPLETED" -> PaymentTermStatus.PAID;
                case "MARK_AS_ARCHIVED" -> PaymentTermStatus.ARCHIVED;
                default -> throw new APIException("Invalid update type: " + updateType);
            };
            for (PaymentCycle item : paymentCycles) {
                if (item.getRemainAmount() == 0) {
                    item.setStatus(PaymentTermStatus.PAID);
                } else {
                    item.setActive(active);
                    item.setStatus(updateTypeValue);
                }
            }
            List<PaymentCycle> updatedItems = paymentCycleRepository.saveAll(paymentCycles);
            return paymentCycleMapper.toDTOList(updatedItems);
        }
    }

    private BasePageResponse<PaymentCycleDTO> buildPageResponse(Page<PaymentCycle> page) {

        List<PaymentCycleDTO> paymentCycleDTOList = paymentCycleMapper.toDTOList(page.getContent());

        List<String> customerIds = paymentCycleDTOList.stream()
                .map(PaymentCycleDTO::getCustomerId)
                .distinct()
                .toList();

        List<String> buShortNames = paymentCycleDTOList.stream()
                .map(PaymentCycleDTO::getBuId)
                .distinct()
                .toList();

        List<String> serviceIds = paymentCycleDTOList.stream()
                .map(PaymentCycleDTO::getServiceId)
                .distinct()
                .toList();

        Map<String, Object> customerNames = customerInfoService.getBatchCustomerTitle(customerIds);
        Map<String, Object> buFullNames = basementService.getBatchBuFullNames(buShortNames);
        Map<String, Object> serviceNames = productService.getBatchByServiceName(serviceIds);
        for (PaymentCycleDTO dto : paymentCycleDTOList) {
            String customerName = (String) customerNames.getOrDefault(dto.getCustomerId(), "Unknown Customer");
            String buName = (String) buFullNames.getOrDefault(dto.getBuId(), "Unknown Bu");
            String serviceNameValue = (String) serviceNames.getOrDefault(dto.getServiceId(), "Unknown Service");
            dto.setCustomerName(customerName);
            dto.setBasementName(buName);
            dto.setServiceName(serviceNameValue);
        }
        return new BasePageResponse<>(
                paymentCycleDTOList,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }


    @Override
    public List<PaymentCycleDTO> getPaymentCycleByContractId(String contractId) {
        List<PaymentCycle> paymentCycles = paymentCycleRepository.getByContractId(contractId);
        if (paymentCycles.isEmpty()) {
            throw new APIException("Cant find any payment with contractCode is: " + contractId);
        }

        List<PaymentCycleDTO> paymentCycleDTOS = paymentCycleMapper.toDTOList(paymentCycles);

        Set<String> customerIds = contractUtilService.extractIds(paymentCycleDTOS, PaymentCycleDTO::getCustomerId);
        Set<String> buIds = contractUtilService.extractIds(paymentCycleDTOS, PaymentCycleDTO::getBuId);
        Set<String> serviceIds = contractUtilService.extractIds(paymentCycleDTOS, PaymentCycleDTO::getServiceId);

        CompletableFuture<Map<String, String>> customerNamesFuture = CompletableFuture.supplyAsync(() ->
                contractUtilService.buildReferenceMap(customerInfoRepository.findAllById(customerIds),
                        CustomerInfo::get_id,CustomerInfo::getCustomerTitle));

        CompletableFuture<Map<String, String>> buNameFuture = CompletableFuture.supplyAsync(() ->
                contractUtilService.buildReferenceMap(branchUnitRepository.findAllById(buIds),
                        BranchUnit::getId,BranchUnit::getBuFullName));

        CompletableFuture<Map<String, String>> serviceName = CompletableFuture.supplyAsync(() ->
                contractUtilService.buildReferenceMap(productRepository.findAllById(serviceIds),
                        ProductModel::get_id,ProductModel::getServiceName));

        for (PaymentCycleDTO dto : paymentCycleDTOS) {
            String customerName = customerNamesFuture.join().getOrDefault(dto.getCustomerId(), "Unknown Customer");
            String buName = buNameFuture.join().getOrDefault(dto.getBuId(), "Unknown Bu");
            String serviceNameValue = serviceName.join().getOrDefault(dto.getServiceId(), "Unknown Service");
            dto.setCustomerName(customerName);
            dto.setBasementName(buName);
            dto.setServiceName(serviceNameValue);
        }

        return paymentCycleDTOS;
    }

    @Override
    public PaymentCycleDTO updatePaymentCycle(String id, PaymentCyclePatchRequestDto patchRequestDto) {
        PaymentCycle updateItem = paymentCycleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentCycle", "_id", id));
        paymentCycleMapper.updatePaymentCycle(patchRequestDto, updateItem);
        PaymentCycle updatedItem = paymentCycleRepository.save(updateItem);

        return paymentCycleMapper.toDTO(updatedItem);
    }

    @Override
    public String deletePaymentCycle(String id) {
        paymentCycleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentCycle", "_id", id));
        paymentCycleRepository.deleteById(id);
        return "Deleted paymentCycle successfully!";
    }

    @Override
    public void deletePaymentCyclesByAppendixId(String appendixId) {
        List<PaymentCycle> paymentCycles = paymentCycleRepository.getByAppendixId(appendixId);
        if (paymentCycles.isEmpty()) {
            throw new APIException("Cant find any payment with appendixId is: " + appendixId);
        } else {
            paymentCycleRepository.deleteAll(paymentCycles);
        }
    }

    @Override
    public Double remainingPaymentByAppendixId(String appendixCode) {
        Appendix appendix = appendixRepository.findByAppendixCode(appendixCode);
        if (appendix == null) {
            throw new ResourceNotFoundException("Appendix", "appendixCode", appendixCode);
        }

        if (!appendix.getActive()) {
            throw  new APIException("Appendix is not active");
        }

        List<PaymentCycle> paymentCycles = paymentCycleRepository.getByAppendixId(appendixCode);
        if (paymentCycles.isEmpty()) {
            throw new APIException("Cant find any payment with contractCode is: " + appendixCode);
        }

        Double remainValue;
        remainValue = paymentCycleRepository.calculateTotalRemainingAmount(appendixCode);

        return remainValue;
    }

}
