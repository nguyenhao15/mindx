package com.example.demo01.domains.MiniCrm.Contract.service.impl;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.core.Aws3.service.S3ServiceImpl;
import com.example.demo01.core.Basement.model.BranchUnit;
import com.example.demo01.core.Basement.repository.BranchUnitRepository;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixRequest;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixRequestFullPayload;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixResponseFullPayload;
import com.example.demo01.domains.MiniCrm.Contract.dtos.contracts.ContractConfigAction;
import com.example.demo01.domains.MiniCrm.Contract.dtos.contracts.ContractDTO;
import com.example.demo01.domains.MiniCrm.Contract.dtos.contracts.ContractPatchRequestDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.contracts.ContractRequestDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleBulkGen;
import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection.TerminateAction;
import com.example.demo01.domains.MiniCrm.Contract.mappers.AppendixMapper;
import com.example.demo01.domains.MiniCrm.Contract.models.Appendix;
import com.example.demo01.domains.MiniCrm.Contract.models.AppendixStatus;
import com.example.demo01.domains.MiniCrm.Contract.repository.AppendixRepository;
import com.example.demo01.domains.MiniCrm.Contract.repository.ContractRepository;
import com.example.demo01.domains.MiniCrm.Contract.service.AppendixService;
import com.example.demo01.domains.MiniCrm.Contract.service.ContractServices;
import com.example.demo01.domains.MiniCrm.Contract.service.ContractUtilService;
import com.example.demo01.domains.MiniCrm.Contract.service.PaymentCycleService;
import com.example.demo01.core.SpaceCustomer.models.CustomerInfo;
import com.example.demo01.core.SpaceCustomer.repository.CustomerInfoRepository;
import com.example.demo01.core.SpaceCustomer.service.CustomerInfoService;
import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Products.ProductInfoDto;
import com.example.demo01.domains.MiniCrm.Dimmesion.model.ProductModel;
import com.example.demo01.domains.MiniCrm.Dimmesion.repository.ProductRepository;
import com.example.demo01.domains.MiniCrm.Dimmesion.service.ProductService;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.UpdatePaymentTermDetail;
import com.example.demo01.domains.MiniCrm.Payment.service.TransactionService;
import com.example.demo01.domains.MiniCrm.ProfitAndLost.dtos.AllocationObject;
import com.example.demo01.domains.MiniCrm.ProfitAndLost.model.ProfitAndLost;
import com.example.demo01.domains.MiniCrm.ProfitAndLost.repository.ProfitAndLostRepository;
import com.example.demo01.domains.MiniCrm.ProfitAndLost.service.ProfitAndLossService;
import com.example.demo01.domains.MiniCrm.Renting.dtos.RentingAction;
import com.example.demo01.domains.MiniCrm.Renting.dtos.RentingDto;
import com.example.demo01.domains.MiniCrm.Renting.mapper.RentingMapper;
import com.example.demo01.domains.MiniCrm.Renting.model.RentingActionEnum;
import com.example.demo01.domains.MiniCrm.Renting.model.RentingStatus;
import com.example.demo01.domains.MiniCrm.Renting.service.RentingContractService;
import com.example.demo01.core.Exceptions.APIException;
import com.example.demo01.core.Exceptions.DuplicateResourceException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.MiniCrm.Utils.MC_Utils;
import com.example.demo01.utils.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AppendixServiceImpl implements AppendixService {

    private final AppendixRepository appendixRepository;

    private final AppendixMapper appendixMapper;

    private final PaymentCycleService paymentCycleService;

    private final CustomerInfoService customerInfoService;

    private final ContractUtilService contractUtilService;

    private final ProfitAndLossService profitAndLossService;

    private final RentingContractService rentingContractService;

    private final ProfitAndLostRepository profitAndLostRepository;

    private final ContractServices contractServices;

    private final RentingMapper rentingMapper;

    private final BasementService basementService;

    private final ProductService productService;

    private final ContractRepository contractRepository;

    private final MC_Utils mcUtils;

    private final AppUtil appUtil;

    private final S3ServiceImpl s3Service;

    private final CustomerInfoRepository customerInfoRepository;

    private final BranchUnitRepository branchUnitRepository;

    private final ProductRepository productRepository;

    private final MongoTemplate mongoTemplate;

    private final TransactionService transactionService;


    private static @NotNull PaymentCycleBulkGen getPaymentCycleBulkGen(AppendixRequest requestDto) {
        PaymentCycleBulkGen handlePaymentCycleGen = new PaymentCycleBulkGen();
        handlePaymentCycleGen.setStartDate(requestDto.getActivationDate());
        handlePaymentCycleGen.setEndDate(requestDto.getEndDate());
        handlePaymentCycleGen.setPaymentTerm(requestDto.getPaymentTerms());
        handlePaymentCycleGen.setValuePerMonth(requestDto.getMonthlyRenting());
        handlePaymentCycleGen.setTotalValue(requestDto.getTotalValue());
        handlePaymentCycleGen.setPaidAmount(requestDto.getPaidAmount());
        handlePaymentCycleGen.setCurrencyId(requestDto.getCurrencyCode());
        handlePaymentCycleGen.setServiceId(requestDto.getServiceId());
        handlePaymentCycleGen.setBuId(requestDto.getBuId());
        handlePaymentCycleGen.setContractCode(requestDto.getContractCode());
        handlePaymentCycleGen.setCustomerId(requestDto.getCustomerId());
        handlePaymentCycleGen.setInvoiceCheck(requestDto.getInvoiceCheck());
        return handlePaymentCycleGen;
    }

    @Override
    public AppendixResponseFullPayload previewAppendix(@NonNull AppendixRequest appendixRequest) {
        String productId = appendixRequest.getServiceId();
        LocalDate activeDate = appendixRequest.getActivationDate();
        LocalDate endDate = appendixRequest.getEndDate();
        Double monthlyValue = appendixRequest.getMonthlyRenting();
        String contractCode = appendixRequest.getContractCode();
        String buShortName = appendixRequest.getBuId();
        String customerId = appendixRequest.getCustomerId();
        String agreementNumber = appendixRequest.getAgreementNumber();

        if (Objects.equals(appendixRequest.getAppendixTag(), "NEW")) {
            contractCode = mcUtils.contractCodeGen(buShortName,activeDate, Integer.parseInt(appendixRequest.getAgreementNumber()));
            agreementNumber = appendixRequest.getAgreementNumber();
            boolean isDup = contractRepository.findByContractCode(contractCode).isPresent();
            if (isDup) {
                throw new DuplicateResourceException("Contract with same contract code already exists: "+contractCode);
            }
        }

        String appendixCode = mcUtils.appendixCodeGen(agreementNumber, contractCode);

        Optional<Appendix> appendixOptional = appendixRepository.findById(appendixCode);

        if (appendixOptional.isPresent()) {
            throw new DuplicateResourceException("Appendix with same agreement number already exists");
        }

        ProductInfoDto selectedProduct = productService.getProductById(productId);

        String getServiceUnit = selectedProduct.getServiceUnit();
        Double totalValue = mcUtils.calTotalValue(activeDate,endDate,monthlyValue, getServiceUnit );

        appendixRequest.setAppendixCode(appendixCode);
        appendixRequest.setTotalValue(totalValue);
        appendixRequest.setMonthlyRenting(monthlyValue);
        appendixRequest.setContractCode(contractCode);


        Double newPaidAmount = getDiffAmountAppendix(appendixRequest);
        appendixRequest.setPaidAmount(newPaidAmount);


        PaymentCycleBulkGen handlePaymentCycleGen = getPaymentCycleBulkGen(appendixRequest);
        List<PaymentCycleDTO> paymentCycleDTOList = paymentCycleService.handleGeneratePaymentCyclePreview(handlePaymentCycleGen);

        AppendixInfoDto appendixInfoDto = appendixMapper.toDtoFromRequest(appendixRequest);

        String buName = basementService.getBuInfoByShortName(buShortName).getBuFullName();
        String customerName = customerInfoService.findCustomerById(customerId).getCustomerTitle();
        String productName = selectedProduct.getServiceName();

        appendixInfoDto.setBuName(buName);
        appendixInfoDto.setCustomerName(customerName);
        appendixInfoDto.setServiceName(productName);

        List<RentingDto> rentingList = new ArrayList<>();
        List<RentingAction> rentingActions = new ArrayList<>();

        if (appendixRequest.getRentingActions() != null) {
            for (RentingAction actionItem : appendixRequest.getRentingActions().stream().filter(
                    p -> !Objects.equals(p.get_id(), null)
            ).toList()) {
                RentingDto rentingDto =  rentingContractService.previewRentingRequest(appendixRequest, rentingMapper.toRequest(actionItem));
                rentingList.add(rentingDto);
                rentingActions.add(actionItem);
            }
        }

        if (appendixRequest.getRoomId() != null) {
            for (String roomId : appendixRequest.getRoomId()) {
                RentingAction rentingAction = new RentingAction();
                rentingAction.setBuId(buShortName);
                rentingAction.setCustomerId(customerId);
                rentingAction.setAssigned_to(endDate);
                rentingAction.setAssigned_from(activeDate);
                rentingAction.setActionType(RentingActionEnum.NEW);
                rentingAction.setActive(false);
                rentingAction.setStatus(RentingStatus.PENDING);
                rentingAction.setContractId(contractCode);
                rentingAction.setRoomShortName(roomId);
                rentingAction.setServiceShortName(productId);
                RentingDto rentingDto = rentingContractService.previewRentingRequest(appendixRequest, rentingMapper.toRequest(rentingAction));
                rentingActions.add(rentingAction);
                rentingList.add(rentingDto);
            }
        }

        ContractDTO contractDTO;

        if (appendixRequest.getAppendixTag().equals("NEW")) {
            contractDTO = contractServices.createPreviewContract(appendixRequest);
        } else {
            contractDTO = contractServices.getContractByCode(contractCode);
            contractDTO.setServiceId(productId);
            contractDTO.setBuId(buShortName);
            contractDTO.setCustomerId(customerId);
            contractDTO.setEndDate(endDate);
        }

        appendixInfoDto.setRentingActions(rentingActions);

        AppendixResponseFullPayload appendixResponseFullPayload = new AppendixResponseFullPayload();

        appendixResponseFullPayload.setContractDTO(contractDTO);
        appendixResponseFullPayload.setAppendixInfoDto(appendixInfoDto);
        appendixResponseFullPayload.setPaymentCycleDTOList(paymentCycleDTOList);
        appendixResponseFullPayload.setRentingDtoList(rentingList);

        return appendixResponseFullPayload;
    }

    @Override
//    @Transactional
    public AppendixResponseFullPayload createAppendix(AppendixRequestFullPayload requestFullPayload, @Nullable MultipartFile file) {
        try {
            AppendixRequest appendixRequest = requestFullPayload.getAppendixRequest();
            List<PaymentCycleDTO> paymentCycleDTOList = requestFullPayload.getPaymentCycleDTOList();
            ContractRequestDto contractRequestDto = requestFullPayload.getContractRequest();
            List<RentingAction> rentingActions = appendixRequest.getRentingActions();
            List<RentingDto> rentingList = new ArrayList<>();

            Double monthlyValue = paymentCycleDTOList.get(0).getMonthlyAmount();
            Double paidAmount = appendixRequest.getPaidAmount();
            String updateAppendixId = appendixRequest.getUpdateAppendix();

            Appendix appendix = appendixMapper.toEntity(appendixRequest);

            for (PaymentCycleDTO paymentCycleDTO : paymentCycleDTOList) {
                paymentCycleDTO.setAppendixId(appendix.getAppendixCode());
            }

            Double renewValueFromPaymentCycle = paymentCycleDTOList.stream()
                    .filter(p -> appendix.getActivationDate().equals(p.getCycleDueDate()))
                    .findFirst()
                    .map(PaymentCycleDTO::getTotalAmount)
                    .orElse(appendix.getTotalValue());

            appendix.setRenewValue(renewValueFromPaymentCycle);

            if (rentingActions != null) {
                for (RentingAction actionItem : rentingActions) {
                    RentingDto rentingDto = rentingContractService.handleRentingItemFromAction(appendixRequest, actionItem);
                    actionItem.set_id(rentingDto.get_id());
                    rentingList.add(rentingDto);
                }
            }
            ContractDTO contractItem;

            appendix.setRentingActions(rentingActions);

            ContractConfigAction configActions = new ContractConfigAction();

            if (Objects.equals(appendixRequest.getAppendixTag(), "NEW")) {
                contractRequestDto.setUpdateType("NEW");
                contractItem = contractServices.createContractToDatabase(contractRequestDto);
                configActions.setServiceId(appendixRequest.getServiceId());
                configActions.setConfigType("NEW");
                configActions.setUpdatePaidAmount(paidAmount);
                configActions.setCustomerId(appendix.getCustomerId());
                configActions.setMonthlyValue(monthlyValue);
                appendix.setConfigActions(configActions);
            } else {
                contractItem = contractServices.getContractByCode(appendix.getContractCode());
                configActions.setServiceId(appendixRequest.getServiceId());
                configActions.setPreviousConfigId(updateAppendixId);
                configActions.setConfigType(appendixRequest.getAppendixTag());
                configActions.setCustomerId(appendix.getCustomerId());
                configActions.setMonthlyValue(monthlyValue);
                configActions.setUpdatePaidAmount(paidAmount);
                appendix.setConfigActions(configActions);
            }

            forceUpdateAppendix(appendix.getContractCode());

            if (paidAmount > 0 ) {
                List<TransactionInfoDTO > refundTransactionList = transactionService.getTransactionsForRefundByPaymentId(appendixRequest, true);
                paymentCycleService.refundPaymentHandle(updateAppendixId, refundTransactionList);
                for (TransactionInfoDTO transactionInfoDTO : refundTransactionList) {
                    for (PaymentCycleDTO paymentCycleDTO : paymentCycleDTOList) {
                        Double updateAmount = Math.min(transactionInfoDTO.getAmount() , paymentCycleDTO.getTotalAmount());
                        AllocationObject allocationObject = profitAndLossService.getAllocationObject(updateAmount, transactionInfoDTO.getExchangeRate(), paymentCycleDTO);
                        profitAndLossService.handleAllocate(allocationObject);
                    }
                }
            };

            List<PaymentCycleDTO> createPaymentCycleList = paymentCycleService.createPaymentCycles(paymentCycleDTOList);

            if (file != null) {
                FileResponseDTO fileResponseDTO = s3Service.uploadContractFile(appendix.getAppendixCode(), file);
                appendix.setFileResponseDTO(fileResponseDTO);
            } else {
                appendix.setFileResponseDTO(null);
            }

            Appendix savedAppendix = appendixRepository.save(appendix);

            AppendixInfoDto appendixInfoDto = appendixMapper.toDto(savedAppendix);
            AppendixResponseFullPayload appendixResponseFullPayload = new AppendixResponseFullPayload();

            appendixResponseFullPayload.setAppendixInfoDto(appendixInfoDto);
            appendixResponseFullPayload.setPaymentCycleDTOList(createPaymentCycleList);
            appendixResponseFullPayload.setRentingDtoList(rentingList);
            appendixResponseFullPayload.setContractDTO(contractItem);
            return appendixResponseFullPayload;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create appendix: " + e.getMessage());
        }
    }

    @Override
    public AppendixInfoDto getAppendixByAppendixCode(String appendixCode) {
        Appendix appendix = appendixRepository.findByAppendixCode(appendixCode);
        if (appendix == null) {
            throw new ResourceNotFoundException("Appendix", "appendixCode", appendixCode);
        }
        return appendixMapper.toDto(appendix);
    }

    @Override
    public AppendixResponseFullPayload getAppendixById(String appendixId) {
        Appendix appendix;
        Optional<Appendix> findAppendix = appendixRepository.findById(appendixId);

        appendix = findAppendix.orElseGet(() -> appendixRepository.findByAppendixCode(appendixId));

        if (appendix == null) {
            throw new ResourceNotFoundException("Appendix", "id or appendixCode", appendixId);
        }

        ContractDTO contractDTO = contractServices.getContractByCode(appendix.getContractCode());

        String appendixCode =  appendix.getAppendixCode();
        FileResponseDTO fileResponseDTO = appendix.getFileResponseDTO();
        String pdfPathValue = appendix.getFileResponseDTO().getFileName();
        List<PaymentCycleDTO> paymentCycleDTOList = paymentCycleService.getPaymentCycleByAppendixId(appendixCode);

        List<RentingDto> rentingDtoList = rentingContractService.getRentingByContractId(appendix.getContractCode());

        List<TransactionInfoDTO> transactionList = transactionService.getTransactionsByPaymentId(appendixCode);

        AppendixInfoDto appendixInfoDto = appendixMapper.toDto(appendix);

        String url = s3Service.getFileUrl(pdfPathValue, 15L );

        fileResponseDTO.setFileUrl(url);

        appendixInfoDto.setFileResponseDTO(fileResponseDTO);

        String buName = basementService.getBuInfoByShortName(appendix.getBuId()).getBuFullName();
        String customerName = customerInfoService.findCustomerById(appendix.getCustomerId()).getCustomerTitle();
        String productName = productService.getProductById(appendix.getServiceId()).getServiceName();

        appendixInfoDto.setBuName(buName);
        appendixInfoDto.setCustomerName(customerName);
        appendixInfoDto.setServiceName(productName);

        AppendixResponseFullPayload appendixResponseFullPayload = new AppendixResponseFullPayload();

        appendixResponseFullPayload.setAppendixInfoDto(appendixInfoDto);
        appendixResponseFullPayload.setTransactionList(transactionList);
        appendixResponseFullPayload.setRentingDtoList(rentingDtoList);
        appendixResponseFullPayload.setPaymentCycleDTOList(paymentCycleDTOList);
        appendixResponseFullPayload.setContractDTO(contractDTO);
        return appendixResponseFullPayload;
    }

    @Override
    public AppendixInfoDto getAppendixInfoInGraphById(String appendixCode) {
        Appendix appendix = appendixRepository.findById(appendixCode)
                .orElseThrow(() -> new ResourceNotFoundException("Appendix", "appendixCode", appendixCode));
        return appendixMapper.toDto(appendix);
    }

    @Override
    public AppendixInfoDto rollBackAppendix(String appendixCode) {
        Appendix appendix = appendixRepository.findByAppendixCode(appendixCode);
        if (appendix == null) {
            throw  new ResourceNotFoundException("Appendix", "appendixCode", appendixCode);
        }
        ContractConfigAction rollBackAction = appendix.getConfigActions();
        List<RentingAction> rentingActions = appendix.getRentingActions();

        for (RentingAction rentingAction : rentingActions) {
            if (Objects.requireNonNull(rentingAction.getActionType()) == RentingActionEnum.NEW) {
                rentingContractService.deleteRentingById(rentingAction.get_id());
            }
        }

        if (Objects.equals(rollBackAction.getConfigType(), "NEW")) {
            deleteAppendix(appendix.get_id());
            return appendixMapper.toDto(appendix);
        }

        String preAppendix = rollBackAction.getPreviousConfigId();
        Double paidAmount = rollBackAction.getUpdatePaidAmount();

        Appendix updateAppendix = appendixRepository.findByAppendixCode(preAppendix);
        AppendixRequest appendixRequest = new AppendixRequest();

        List<RentingAction> rollBackRentingActions = updateAppendix.getRentingActions();

        appendixRequest.setPaidAmount(paidAmount);
        appendixRequest.setAppendixCode(preAppendix);
        appendixRequest.setUpdateAppendix(appendixCode);
        appendixRequest.setCustomerId(updateAppendix.getCustomerId());
        appendixRequest.setBuId(updateAppendix.getBuId());
        appendixRequest.setServiceId(updateAppendix.getServiceId());
        appendixRequest.setActivationDate(updateAppendix.getActivationDate());
        appendixRequest.setEndDate(updateAppendix.getEndDate());

        for (RentingAction rentingAction : rollBackRentingActions) {
            rentingContractService.handleRentingItemFromAction(appendixRequest, rentingAction);
        }
        List<TransactionInfoDTO> transactionInfoDTOS = transactionService.getTransactionsForRefundByPaymentId(appendixRequest, false);
        for (TransactionInfoDTO transactionInfoDTO : transactionInfoDTOS) {
            paymentCycleService.handleAddPayment(preAppendix, transactionInfoDTO.getAmount(), transactionInfoDTO.getExchangeRate());
        }

        deleteAppendix(appendix.get_id());

        return appendixMapper.toDto(updateAppendix);
    }

    private BasePageResponse<AppendixInfoDto> buildAppendixPageResponse(@NonNull Page<Appendix> pageable) {
        List<AppendixInfoDto> appendixInfoList = appendixMapper.toDtoList(pageable.getContent());

        List<String> customerIds = appendixInfoList.stream()
                .map(AppendixInfoDto::getCustomerId)
                .distinct()
                .toList();

        List<String> buShortNames = appendixInfoList.stream()
                .map(AppendixInfoDto::getBuId)
                .distinct()
                .toList();

        List<String> serviceIds = appendixInfoList.stream()
                .map(AppendixInfoDto::getServiceId)
                .distinct()
                .toList();

        Map<String, Object> customerNames = customerInfoService.getBatchCustomerTitle(customerIds);
        Map<String, Object> buFullNames = basementService.getBatchBuFullNames(buShortNames);
        Map<String, Object> serviceNames = productService.getBatchByServiceName(serviceIds);

        List<AppendixInfoDto> handedArr = appendixInfoList.stream().peek(ap -> {
            String customerName = (String) customerNames.get(ap.getCustomerId());
            String buFullName = (String) buFullNames.get(ap.getBuId());
            String serviceName = (String) serviceNames.get(ap.getServiceId());

            ap.setServiceName(serviceName);
            ap.setBuName(buFullName);
            ap.setCustomerName(customerName);
        }).toList();

        return new BasePageResponse<>(
                    handedArr,
                    pageable.getNumber(),
                    pageable.getSize(),
                    pageable.getTotalElements(),
                    pageable.getTotalPages(),
                    pageable.isLast());
    }

    @Override
    public BasePageResponse<AppendixInfoDto> getActiveAppendix(PageInput pageInput, List<FilterRequest> filter) {
        List<Criteria> baseCriteriaList = new ArrayList<>();
        baseCriteriaList.add(Criteria.where("active").is(true));
        return searchAppendix(filter, baseCriteriaList, pageInput);
    }

    @Override
    public BasePageResponse<AppendixInfoDto> searchAppendix(List<FilterRequest> request, List<Criteria> baseCriteriaList, PageInput pageInput) {
        Page<Appendix> page = appUtil.buildPageResponse(request, baseCriteriaList, pageInput, Appendix.class);
        return buildAppendixPageResponse(page);
    }

    @Override
    public BasePageResponse<AppendixInfoDto> getAllAppendix(PageInput pageInput) {
        Pageable pageable = pageInput.toPageable();
        Page<Appendix> appendixPage = appendixRepository.findAll(pageable);
        return buildAppendixPageResponse(appendixPage);
    }

    @Override
    public List<AppendixInfoDto> getActiveAppendixByBuShortNameAndServiceIdAndCustomerId(String buShortName, String serviceId, String customerId) {
        List<Appendix> activeAppendixList = appendixRepository.getActiveAppendixByBuShortNameAndServiceIDAndCustomerId(buShortName, serviceId, customerId);
        return appendixMapper.toDtoList(activeAppendixList);
    }

    @Override
    public List<AppendixInfoDto> getAppendixByCustomerId(String customerId) {
        List<Appendix> appendixList = appendixRepository.findByCustomerId(customerId);
        if (appendixList.isEmpty()) {
            return List.of();
        }
        return appendixMapper.toDtoList(appendixList);
    }

    @Override
    public List<AppendixInfoDto> getActiveAppendixByCustomerId(String customerId, String buId) {
        return appendixRepository.getActiveAppendixByCustomerIdAndBuId(customerId, buId);
    }

    @Override
    public AppendixResponseFullPayload handlePaymentAppendix(String appendixId, Double paidAmount, Double exchangeRate) {
        if (paidAmount == null || paidAmount <= 0) return null;

        Appendix appendix = appendixRepository.findById(appendixId)
                .orElseThrow(() -> new ResourceNotFoundException("Appendix", "id", appendixId));

        UpdatePaymentTermDetail updatedPaymentCycles = paymentCycleService.handleAddPayment(appendixId, paidAmount, exchangeRate);

        ContractDTO contractDTO = contractServices.getContractByCode(appendix.getContractCode());
        List<RentingDto> rentingDtoList = rentingContractService.getRentingByContractId(appendix.getContractCode());

        AppendixResponseFullPayload appendixResponseFullPayload = new AppendixResponseFullPayload();

        appendixResponseFullPayload.setAppendixInfoDto(appendixMapper.toDto(appendix));
        appendixResponseFullPayload.setPaymentCycleDTOList(updatedPaymentCycles.getPaymentCycleDTOS());
        appendixResponseFullPayload.setContractDTO(contractDTO);
        appendixResponseFullPayload.setRentingDtoList(rentingDtoList);

        return appendixResponseFullPayload;
    }


    @Override
    public List<AppendixInfoDto> getAppendixByContract(String contractCode) {
        List<Appendix> appendixList = appendixRepository.findByContractCode(contractCode);

        if (!appendixList.isEmpty()) {

            Set<String> customerIds = contractUtilService.extractIds(appendixList, Appendix::getCustomerId);

            Set<String> buIds = contractUtilService.extractIds(appendixList, Appendix::getBuId);

            Set<String> serviceIds = contractUtilService.extractIds(appendixList, Appendix::getServiceId);

            CompletableFuture<Map<String, String>> customerNamesFuture = CompletableFuture.supplyAsync(() ->
                    contractUtilService.buildReferenceMap(customerInfoRepository.findAllById(customerIds),
                            CustomerInfo::get_id,CustomerInfo::getCustomerTitle));

            CompletableFuture<Map<String, String>> buNameFuture = CompletableFuture.supplyAsync(() ->
                    contractUtilService.buildReferenceMap(branchUnitRepository.findAllById(buIds),
                            BranchUnit::getId,BranchUnit::getBuFullName));

            CompletableFuture<Map<String, String>> serviceName = CompletableFuture.supplyAsync(() ->
                    contractUtilService.buildReferenceMap(productRepository.findAllById(serviceIds),
                            ProductModel::get_id,ProductModel::getServiceName));

            try {
                CompletableFuture.allOf(customerNamesFuture, buNameFuture, serviceName).join();

                Map<String, String> customerNamesMap = customerNamesFuture.get();
                Map<String, String> buNamesMap = buNameFuture.get();
                Map<String, String> serviceNamesMap = serviceName.get();

                for (Appendix appendix : appendixList) {
                    AppendixInfoDto appendixInfoDto = appendixMapper.toDto(appendix);
                    appendixInfoDto.setCustomerName(customerNamesMap.get(appendix.getCustomerId()));
                    appendixInfoDto.setBuName(buNamesMap.get(appendix.getBuId()));
                    appendixInfoDto.setServiceName(serviceNamesMap.get(appendix.getServiceId()));
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to fetch reference data: " + e.getMessage());
            }
            return appendixMapper.toDtoList(appendixList);
        } else {
        throw new APIException("Appendix list is empty for contract code: " + contractCode);
       }
    }

    @Override
    public AppendixInfoDto updateAppendix(String id, AppendixRequest appendixRequest) {
        Appendix appendix = appendixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appendix", "id", id));
        appendixMapper.updateContractFromEntity(appendixRequest, appendix);

        appendixRepository.save(appendix);
        return appendixMapper.toDto(appendix);
    }

    @Override
    public List<AppendixInfoDto> forceUpdateAppendix(String ContractCode) {
        List<Appendix> appendixes = appendixRepository.findByContractCodeAndActive(ContractCode, true);
        for (Appendix appendix : appendixes) {
            if (!canTransition(appendix.getAppendixStatus(), AppendixStatus.TERMINATED)) {
                throw  new APIException("Appendix status can't be ARCHIVED");
            }
        }

        if (!appendixes.isEmpty()) {
            List<AppendixInfoDto> updatedAppendixes = new ArrayList<>();
            for(Appendix appendix : appendixes) {
                appendix.setActive(false);
                appendix.setAppendixStatus(AppendixStatus.ARCHIVED);
                appendixRepository.save(appendix);
                AppendixInfoDto appendixInfoDto = appendixMapper.toDto(appendix);
                paymentCycleService.updateActivePaymentCyclesByAppendixId(appendix.getAppendixCode(),false, "MARK_AS_ARCHIVED");
                updatedAppendixes.add(appendixInfoDto);
            }
            return updatedAppendixes;
        }
        return null;
    }

    @Override
    public AppendixResponseFullPayload activateAppendix(String appendixId, Boolean active) {
        Appendix appendix = appendixRepository.findByAppendixCode(appendixId);

        if (appendix == null) {
            throw new ResourceNotFoundException("Appendix", "appendix", appendixId);

        }

        if (!canTransition(appendix.getAppendixStatus(), AppendixStatus.ACTIVE)) {
            throw  new APIException("Appendix status can't be ACTIVE");
        }

        appendix.setActive(active);
        appendix.setAppendixStatus(AppendixStatus.ACTIVE);
        Appendix savedAppendix = appendixRepository.save(appendix);
        AppendixInfoDto appendixInfoDto = appendixMapper.toDto(savedAppendix);
        List<PaymentCycleDTO> updatedPaymentCycles = paymentCycleService.updateActivePaymentCyclesByAppendixId(appendixId, active, "MARK_AS_ACTIVE");
        List<RentingAction> actions =  appendix.getRentingActions();
        List<RentingDto> rentingDtoList = new ArrayList<>();

        if (actions != null && !actions.isEmpty()) {
            List<String> ids = actions.stream().map(RentingAction::get_id).toList();
            List<RentingDto> rentingDtos = rentingContractService.updateRentingActiveStatus(ids, active);
            rentingDtoList.addAll(rentingDtos);
        }

        AppendixResponseFullPayload appendixResponseFullPayload = new AppendixResponseFullPayload();

        appendixResponseFullPayload.setAppendixInfoDto(appendixInfoDto);
        appendixResponseFullPayload.setRentingDtoList(rentingDtoList);
        appendixResponseFullPayload.setPaymentCycleDTOList(updatedPaymentCycles);

        return appendixResponseFullPayload;
    }

    @Override
    public void updateUnActiveAppendixByContractId(String appendixCode, AppendixStatus appendixStatus) {
        Appendix appendix = appendixRepository.findByAppendixCode(appendixCode);

        AppendixStatus appendixStatusString = appendix.getAppendixStatus();

        if (canTransition(appendixStatusString, appendixStatus)) {
            appendix.setActive(false);
            appendix.setAppendixStatus(appendixStatus);
            appendixRepository.save(appendix);
            paymentCycleService.updateActivePaymentCyclesByAppendixId(appendixCode, false, "MARK_AS_COMPLETED");
        } else  {
            throw new APIException("Invalid status transition from " + appendixStatusString + " to " + appendixStatus);
        }


    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public List<TerminateAction> terminateAppendix(String appendixCode, LocalDate terminationDate) {
        AppendixInfoDto appendixInfoDto = getAppendixByAppendixCode(appendixCode);
        ContractDTO contractDTO = contractServices.getContractByCode(appendixInfoDto.getContractCode());
        String contractId = contractDTO.get_id();
        String contractCode = contractDTO.getContractCode();
        ContractPatchRequestDto contractPatchRequestDto = new ContractPatchRequestDto();
        if (!canTransition(appendixInfoDto.getAppendixStatus(), AppendixStatus.TERMINATED)) {
            throw new APIException("Invalid status transition from " + appendixInfoDto.getAppendixStatus());
        }

        List<TerminateAction> terminateActions = new ArrayList<>();

        contractPatchRequestDto.setContractStatus("TERMINATED");

        contractServices.updateContractById(contractId, contractPatchRequestDto);
        terminateActions.add(
                new TerminateAction(
                        "TERMINATE_CONTRACT",
                        "TERMINATED",
                        contractId
                )
        );

        List<PaymentCycleDTO> paymentCycles = paymentCycleService.updateActivePaymentCyclesByAppendixId(appendixCode,false, "MARK_AS_ARCHIVED");

        for (PaymentCycleDTO paymentCycleDTO : paymentCycles) {
            terminateActions.add(
                    new TerminateAction(
                            "TERMINATE_PAYMENT_CYCLE",
                            "ARCHIVED",
                            paymentCycleDTO.get_id()
                    )
            );
        }

        List<RentingAction> actions =  appendixInfoDto.getRentingActions();
        if (actions != null && !actions.isEmpty()) {
           List<RentingAction> rentingActions = actions.stream().filter(p -> !p.getActionType().equals(RentingActionEnum.TERMINATE)).toList();
           if (!rentingActions.isEmpty()) {
               List<RentingDto> terminateRentingItems = rentingContractService.updateUnActiveRentingByContractId(contractCode, RentingStatus.CANCELED, terminationDate);
               for (RentingDto rentingDto : terminateRentingItems) {
                     terminateActions.add(
                            new TerminateAction(
                                      "TERMINATE_RENTING",
                                      "CANCELLED",
                                      rentingDto.get_id()
                            )
                     );
               }
           }
        }

        updateAppendix(appendixInfoDto.get_id(), new AppendixRequest() {{
            setAppendixStatus(AppendixStatus.TERMINATED);
            setActive(false);
        }});
        terminateActions.add(
                new TerminateAction(
                        "TERMINATE_APPENDIX",
                        "TERMINATED",
                        appendixInfoDto.get_id()
                )
        );

        return terminateActions;
    }

    @Override
    public void deleteAppendix(String appendixId) {
        Appendix appendix = appendixRepository.findById(appendixId)
                .orElseThrow(() -> new ResourceNotFoundException("Appendix", "id", appendixId));
        paymentCycleService.deletePaymentCyclesByAppendixId(appendix.getAppendixCode());
        if (Objects.equals(appendix.getConfigActions().getConfigType(), "NEW")) {
            ContractDTO contractDTO = contractServices.getContractByCode(appendix.getContractCode());
            contractRepository.deleteById(contractDTO.get_id());
        }
        transactionService.deleteTransactionByPaymentId(appendixId);

        s3Service.deleteFile(appendix.getFileResponseDTO().getFileName());
        appendixRepository.delete(appendix);
    }

    @Override
    public FileResponseDTO uploadAppendixFile(String appendixId, MultipartFile file) throws IOException {
        Appendix appendix = appendixRepository.findById(appendixId)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", "_id", appendixId));

        String existingFilePath = appendix.getFileResponseDTO().getFileName();

        if (existingFilePath != null && !existingFilePath.isEmpty()) {
            s3Service.deleteFile(existingFilePath);
        }

        String fileName = appendix.getContractCode() + "_" + appUtil.handleSubString(UUID.randomUUID().toString(),5,true);

        FileResponseDTO fileResponseDTO = s3Service.uploadContractFile(fileName,file);
        appendix.setFileResponseDTO(fileResponseDTO);
        appendixRepository.save(appendix);
        return fileResponseDTO;
    }

    @Override
    public Double getDiffAmountAppendix(AppendixRequest appendixRequest) {

        LocalDate activeDate = appendixRequest.getActivationDate();
        String contractCode = appendixRequest.getContractCode();
        int totalDaysInMonth = activeDate.lengthOfMonth();
        LocalDate minActivationDate = activeDate.minusDays(activeDate.getDayOfMonth());
        Double totalDeduction = 0.0;
        List<ProfitAndLost> profitAndLostList = profitAndLostRepository.findByContractIdAndAllocationDateAfter(contractCode, minActivationDate);
        for (ProfitAndLost profitAndLost : profitAndLostList) {
            if (profitAndLost.getAllocationDate().isAfter(activeDate.with(TemporalAdjusters.lastDayOfMonth()))) {
                totalDeduction += profitAndLost.getAllocatedAmount();
            } else if (profitAndLost.getAllocationDate().isAfter(minActivationDate) && profitAndLost.getAllocationDate().isBefore(activeDate)) {
                int daysOwnedByNewAppendix = totalDaysInMonth - activeDate.getDayOfMonth() + 1;
                double dailyAmount = profitAndLost.getAllocatedAmount() / totalDaysInMonth;
                double deductionAmount = dailyAmount * daysOwnedByNewAppendix;
                totalDeduction += deductionAmount;
            }
        }
        return totalDeduction;
    }

    private static final Map<AppendixStatus, List<AppendixStatus>> ALLOWED_TRANSITIONS = new EnumMap<>(AppendixStatus.class);

    static {
        ALLOWED_TRANSITIONS.put(AppendixStatus.ACTIVE, List.of(AppendixStatus.DRAFT, AppendixStatus.TERMINATED, AppendixStatus.COMPLETED));
        ALLOWED_TRANSITIONS.put(AppendixStatus.DRAFT, List.of(AppendixStatus.ACTIVE, AppendixStatus.TERMINATED));
        ALLOWED_TRANSITIONS.put(AppendixStatus.TERMINATED, List.of(AppendixStatus.ACTIVE, AppendixStatus.COMPLETED));
        ALLOWED_TRANSITIONS.put(AppendixStatus.EXTENSION, List.of(AppendixStatus.ACTIVE, AppendixStatus.COMPLETED));
        ALLOWED_TRANSITIONS.put(AppendixStatus.ARCHIVED, List.of(AppendixStatus.ACTIVE, AppendixStatus.COMPLETED));
        ALLOWED_TRANSITIONS.put(AppendixStatus.COMPLETED, Collections.emptyList());
    }

    private boolean canTransition(AppendixStatus currentStatus, AppendixStatus newStatus) {
        if (currentStatus == null || newStatus == null) return false;
        return ALLOWED_TRANSITIONS.get(currentStatus).contains(newStatus);
    }

}
