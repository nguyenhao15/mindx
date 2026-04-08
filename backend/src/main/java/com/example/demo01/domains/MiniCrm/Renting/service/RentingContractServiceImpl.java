package com.example.demo01.domains.MiniCrm.Renting.service;

import com.example.demo01.core.Basement.dto.room.RoomInfoDto;
import com.example.demo01.core.Basement.mapper.RoomMapper;
import com.example.demo01.core.Basement.model.RoomModel;
import com.example.demo01.repository.mongo.CoreRepo.BasementRepository.BranchUnitRepository;
import com.example.demo01.repository.mongo.CoreRepo.BasementRepository.RoomModelRepository;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.core.Basement.service.RoomCollectionService;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixRequest;
import com.example.demo01.domains.MiniCrm.Contract.service.ContractUtilService;
import com.example.demo01.repository.mongo.CoreRepo.SpaceCustomerRepository.CustomerInfoRepository;
import com.example.demo01.repository.mongo.MiniCrmRepository.dimRepository.ProductRepository;
import com.example.demo01.domains.MiniCrm.Dimmesion.service.ProductService;
import com.example.demo01.domains.MiniCrm.Renting.dtos.*;
import com.example.demo01.domains.MiniCrm.Renting.mapper.RentingMapper;
import com.example.demo01.domains.MiniCrm.Renting.model.RentingActionEnum;
import com.example.demo01.domains.MiniCrm.Renting.model.RentingRecord;
import com.example.demo01.domains.MiniCrm.Renting.model.RentingStatus;
import com.example.demo01.repository.mongo.MiniCrmRepository.RentingRepository.RentingContractRepository;
import com.example.demo01.core.Exceptions.DuplicateResourceException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.utils.PageInput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RentingContractServiceImpl implements RentingContractService {

    private final RentingContractRepository rentingContractRepository;

    private final RentingMapper rentingMapper;

    private final RoomModelRepository roomModelRepository;

    private final CustomerInfoRepository customerInfoRepository;

    private final RoomMapper roomMapper;

    private final ContractUtilService contractUtilService;

    private final ProductService productService;

    private final RoomCollectionService roomCollectionService;

    private final BranchUnitRepository branchUnitRepository;

    private final BasementService basementService;

    private final ProductRepository productRepository;

    @Override
    public RentingDto createRentingRequest(RentingRequest request) {
        RentingRecord rentingRecord = rentingMapper.toEntity(request);
        RentingRecord createdContract = rentingContractRepository.save(rentingRecord);
        return rentingMapper.toDto(createdContract);
    }

    @Override
    public RentingDto previewRentingRequest(AppendixRequest appendixRequest, RentingRequest request) {
        if (request.getActionType().equals("NEW")) {
            RentingRecord rentingRecord = rentingContractRepository.getRoomByRoomShortNameAndActive(request.getRoomShortName(),true);
            if (rentingRecord != null) {
                throw new DuplicateResourceException("Room with room id is already in use!!");
            }
        }

        RentingDto rentingDto = rentingMapper.toDtoPreview(request);

        Boolean activeValue = request.getActive();

        if (!activeValue) {
            request.setStatus(RentingStatus.INACTIVE);
        } else {
            rentingDto.setCustomerId(request.getCustomerId());
            rentingDto.setServiceShortName(appendixRequest.getServiceId());
            rentingDto.setAssigned_to(appendixRequest.getEndDate());
            rentingDto.setActive(true);
        }

        String customerId = rentingDto.getCustomerId();
        String roomId = rentingDto.getRoomShortName();
        String buShortName = rentingDto.getBuId();
        String serviceId = rentingDto.getServiceShortName();

        String customerName = customerInfoRepository.findById(customerId)
                .orElseThrow(() ->  new ResourceNotFoundException("CustomerInfo","_id",customerId)).getCustomerTitle();

        RoomModel roomItem = roomModelRepository.findById(roomId)
                .orElseThrow(() ->  new ResourceNotFoundException("RoomModal","_id", roomId));

        String buName = branchUnitRepository.findByBuId(buShortName).getBuFullName();
        if (buName == null) {
            throw new ResourceNotFoundException("BuName","buShortName",buShortName);
        }

        String serviceName = productRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductModel","_id",serviceId)).getServiceName();

        rentingDto.setRoomName(roomItem.getRoomName());
        rentingDto.setBuName(buName);
        rentingDto.setServiceName(serviceName);
        rentingDto.setCustomerName(customerName);

        return rentingDto;
    }

    @Override
    public RentingDto handleRentingItemFromAction(AppendixRequest appendixRequest, RentingAction request) {
        RentingActionEnum actionType = request.getActionType();
        String rentingId = request.get_id();
        RentingDto result;
        if (Objects.equals(actionType, RentingActionEnum.NEW) ) {
            RentingRequest rentingRequest = rentingMapper.toRequest(request);
            result = createRentingRequest(rentingRequest);
        } else if (Objects.equals(actionType, RentingActionEnum.TERMINATE) ) {
            request.setActualEndDate(appendixRequest.getActivationDate());
            result = updateRentingById(rentingId, rentingMapper.toPatchRequest(request));
        } else {
            request.setAssigned_to(appendixRequest.getEndDate());
            request.setCustomerId(appendixRequest.getCustomerId());
            request.setServiceShortName(appendixRequest.getServiceId());
            result = updateRentingById(rentingId, rentingMapper.toPatchRequest(request));
        }
        return result;
    }

    @Override
    public RentingDto getRentingById(String id) {
        RentingRecord getRentingItem = rentingContractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RentingRecord", "_id", id));
        return rentingMapper.toDto(getRentingItem);
    }

    @Override
    public RentingResponse getAllRenting(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<RentingRecord> page = rentingContractRepository.findAll(pageDetails);

        List<RentingRecord> rentingRecords = page.getContent();
        List<RentingDto> rentingDtos = rentingMapper.toDtoList(rentingRecords);

        RentingResponse rentingResponse = new RentingResponse();
        rentingResponse.setContent(rentingDtos);
        rentingResponse.setPageNumber(page.getNumber());
        rentingResponse.setPageSize(page.getSize());
        rentingResponse.setTotalElements(page.getTotalElements());
        rentingResponse.setTotalPages(page.getTotalPages());
        rentingResponse.setLastPage(page.isLast());
        return rentingResponse;
    }

    @Override
    public List<RentingDto> getRentingByContractId(String contractId) {
        List<RentingRecord> rentingRecords = rentingContractRepository.getByContractId(contractId);
        if (rentingRecords == null) {
            throw new ResourceNotFoundException("RentingRecord", "ContractId", contractId);
        }

        List<RentingDto> rentingDtos = rentingMapper.toDtoList(rentingRecords);

        List<String> roomIds = rentingDtos.stream()
                .map(RentingDto::getRoomShortName)
                .distinct()
                .toList();
        List<String> buIds = rentingDtos.stream()
                .map(RentingDto::getBuId)
                .distinct()
                .toList();

        List<String> serviceIds = rentingDtos.stream()
                .map(RentingDto::getServiceShortName)
                .distinct()
                .toList();

        Map<String, Object> serviceNames = productService.getBatchByServiceName(serviceIds);
        Map<String, Object> buNames = basementService.getBatchBuFullNames(buIds);
        Map<String, Object> roomNames = roomCollectionService.getBatchRoomName(roomIds);

        return rentingDtos.stream().peek(rentingDto -> {
            String roomName = (String) roomNames.get(rentingDto.getRoomShortName());
            String buName = (String) buNames.get(rentingDto.getBuId());
            String serviceName = (String) serviceNames.get(rentingDto.getServiceShortName());
            rentingDto.setServiceName(serviceName);

            rentingDto.setBuName(buName);
            rentingDto.setRoomName(roomName);
        }).toList();
    }

    @Override
    public List<RoomInfoDto> getAvailableRoomForRenting(String buShortName, List<String> roomIds) {
        List<RentingDto> rentingDtoList = getActiveRentingByBuShortName(buShortName);
        Set<String> activeRooms = contractUtilService.extractIds(rentingDtoList, RentingDto::getRoomShortName);
        List<RoomModel> roomModelList = roomModelRepository.getByBuShortName(buShortName);
        return roomModelList.stream()
                .filter(room -> !activeRooms.contains(room.get_id()) || (roomIds != null && roomIds.contains(room.get_id())))
                .map(roomMapper::toDto)
                .toList();
    }

    @Override
    public RentingResponse getRentingByBuShortName(String buShortName, PageInput pageInput) {

        Pageable pageDetails = pageInput.toPageable();
        Page<RentingRecord> page = rentingContractRepository.getByBuId(buShortName, pageDetails);

        List<RentingRecord> rentingRecords = page.getContent();
        List<RentingDto> rentingDtos = rentingMapper.toDtoList(rentingRecords);
        RentingResponse rentingResponse = new RentingResponse();
        rentingResponse.setContent(rentingDtos);
        rentingResponse.setPageNumber(page.getNumber());
        rentingResponse.setPageSize(page.getSize());
        rentingResponse.setTotalElements(page.getTotalElements());
        rentingResponse.setTotalPages(page.getTotalPages());
        rentingResponse.setLastPage(page.isLast());
        return rentingResponse;
    }

    @Override
    public List<RentingDto> getRentingByCustomerId(String customerId) {

        List<RentingRecord> rentingRecords = rentingContractRepository.getByCustomerId(customerId);

        List<RentingDto> rentingDtos = rentingMapper.toDtoList(rentingRecords);

        return rentingDtos;
    }

    @Override
    public RentingResponse getRentingByDefault(String status, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<RentingRecord> page = rentingContractRepository.getByStatus(status, pageDetails);

        List<RentingRecord> rentingRecords = page.getContent();
        List<RentingDto> rentingDtos = rentingMapper.toDtoList(rentingRecords);

        RentingResponse rentingResponse = new RentingResponse();
        rentingResponse.setContent(rentingDtos);
        rentingResponse.setPageNumber(page.getNumber());
        rentingResponse.setPageSize(page.getSize());
        rentingResponse.setTotalElements(page.getTotalElements());
        rentingResponse.setTotalPages(page.getTotalPages());
        rentingResponse.setLastPage(page.isLast());
        return rentingResponse;
    }

    @Override
    public List<RentingDto> getActiveRentingByBuShortName(String buShortName) {
        List<RentingRecord>  rentingRecords = rentingContractRepository.getActiveRentingByBuShortName(buShortName);
        return rentingMapper.toDtoList(rentingRecords);
    }

    @Override
    public RentingDto updateRentingById(String id, RentingPatchRequest request) {
        RentingRecord rentingRecord = rentingContractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RentingRecord", "_id", id));

        rentingMapper.updateRentingFromDto(request, rentingRecord);

        RentingRecord savedItem = rentingContractRepository.save(rentingRecord);

        return rentingMapper.toDto(savedItem);
    }

    @Override
    public String deleteRentingById(String id) {
        rentingContractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RentingRecord", "_id", id));
        rentingContractRepository.deleteById(id);

        return "Deleted item successfully!";
    }

    @Override
    public void deleteByContractId(String contractId) {
        rentingContractRepository.deleteByContractId(contractId);
    }

    @Override
    public List<RentingDto> getBatchRentingByContractCode(String contractCode) {
        if (contractCode == null || contractCode.isEmpty()) {
            return Collections.emptyList();
        }
        return rentingContractRepository.getByContractId(contractCode)
                .stream()
                .map(rentingMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public <T> Map<T, List<RentingDto>> getBatchRenting(List<T> inputs, Function<T, String> contractCodeExtractor) {
        if (inputs == null || inputs.isEmpty()) {
            return Map.of();
        }
        List<String> missingIds = new ArrayList<>();

        List<RentingRecord> entities = rentingContractRepository.findAllByContractIdIn(missingIds);
        Map<String, List<RentingDto>> groupedData = entities.stream()
                .map(rentingMapper::toDto)
                .collect(Collectors.groupingBy(RentingDto::getContractId));

        return inputs.stream().collect(Collectors.toMap(
                input -> input,
                input -> groupedData.get(contractCodeExtractor.apply(input))
        ));
    }

    @Override
    public List<RentingDto> updateRentingActiveStatus(List<String> id, Boolean active) {
        List<RentingRecord> rentingRecord = rentingContractRepository.findAllById(id);
        for (RentingRecord record : rentingRecord) {
            record.setActive(active);
            record.setStatus(RentingStatus.ACTIVE);
        }
        List<RentingRecord> savedItem = rentingContractRepository.saveAll(rentingRecord);
        return rentingMapper.toDtoList(savedItem);
    }

    @Override
    public List<RentingDto> updateUnActiveRentingByContractId(String contractId, RentingStatus updateType, LocalDate updateDate) {
        List<RentingRecord> rentingRecords = rentingContractRepository.getByContractId(contractId);
        for (RentingRecord rentingRecord : rentingRecords) {
            rentingRecord.setActive(false);
            rentingRecord.setStatus(updateType);
            rentingRecord.setActualEndDate(updateDate);
        }
        List<RentingRecord> saveRecords = rentingContractRepository.saveAll(rentingRecords);
        return rentingMapper.toDtoList(saveRecords);
    }

    private static final Map<RentingStatus, List<RentingStatus>> ALLOWED_TRANSITIONS = new EnumMap<>(RentingStatus.class);

    static {
        ALLOWED_TRANSITIONS.put(RentingStatus.ACTIVE, List.of(RentingStatus.CANCELED,RentingStatus.COMPLETED));
        ALLOWED_TRANSITIONS.put(RentingStatus.CANCELED, List.of(RentingStatus.ACTIVE, RentingStatus.COMPLETED));
        ALLOWED_TRANSITIONS.put(RentingStatus.PENDING, List.of(RentingStatus.ACTIVE, RentingStatus.COMPLETED));
        ALLOWED_TRANSITIONS.put(RentingStatus.INACTIVE, List.of(RentingStatus.ACTIVE, RentingStatus.COMPLETED));
        ALLOWED_TRANSITIONS.put(RentingStatus.COMPLETED, List.of(RentingStatus.CANCELED, RentingStatus.ACTIVE));
    }

    private boolean canTransition(RentingStatus currentStatus, RentingStatus newStatus) {
        if (currentStatus == null || newStatus == null) return false;
        return ALLOWED_TRANSITIONS.get(currentStatus).contains(newStatus);
    }

}
