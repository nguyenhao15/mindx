package com.example.demo01.core.Basement.service.Implements;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.configs.SecureRepoConfig.SecurityRepoUtilImpl;
import com.example.demo01.core.Basement.dto.basement.BUInfoDto;
import com.example.demo01.core.Basement.dto.basement.BUPatchRequestDto;
import com.example.demo01.core.Basement.dto.basement.BURequestDto;
import com.example.demo01.core.Basement.mapper.BasementMapper;
import com.example.demo01.core.Basement.model.BranchUnit;
import com.example.demo01.core.Basement.repository.BranchUnitRepository;
import com.example.demo01.core.Basement.repository.PriceRecordRepository;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.core.Basement.service.RoomCollectionService;
import com.example.demo01.core.Exceptions.DuplicateResourceException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.utils.AppUtil;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BUServiceImplement implements BasementService {

    private final BranchUnitRepository branchUnitRepository;

    private final CacheManager cacheManager;

    private final AppUtil appUtil;

    private final BasementMapper basementMapper;

    private final MongoTemplate mongoTemplate;

    private final SecurityRepoUtilImpl securityRepoUtil;

    @Override
    @CacheEvict(value = CacheConstants.BRANCH_CACHE, allEntries = true)
    public BUInfoDto createNewBu(BURequestDto requestDto) {
        try {
            BranchUnit branchUnit = branchUnitRepository.save(basementMapper.toEntity(requestDto));
            return basementMapper.toDto(branchUnit);
        } catch (DuplicateKeyException e) {
            String dupField = appUtil.extractDuplicateField(e.getMessage());
            throw new DuplicateResourceException(String.format("%s đã tồn tại trong hệ thống", dupField));
        }
    }

    @Override
    public void createBus(List<BURequestDto> requestDtos) {
            List<BranchUnit> branchUnits = requestDtos.stream()
                    .map(basementMapper::toEntity)
                    .toList();
            branchUnitRepository.saveAll(branchUnits);
    }

    @Override
    public BUInfoDto getBuInfoById(String id) {
        BranchUnit branchUnit = branchUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BranchModel", "_id", id));

        return basementMapper.toDto(branchUnit);
    }

    @Override
    public BUInfoDto getBuInfoByShortName(String shortName) {
        String CACHE_KEY  = "bu_shortName_" + shortName;
        Cache cache = cacheManager.getCache(CacheConstants.BRANCH_CACHE);
        if (cache != null) {
            BUInfoDto cached = cache.get(CACHE_KEY, BUInfoDto.class);
            if (cached != null) {
                return cached;
            }
        }
        BranchUnit branchUnit = branchUnitRepository.findByBuId(shortName);
        if (branchUnit == null) {
            throw new  ResourceNotFoundException("BranchModel", "buShortName", shortName);
        }
        return basementMapper.toDto(branchUnit);
    }

    @Override
    public List<BUInfoDto> getActiveBuInfos() {
        String CACHE_KEY  = "bu_active_buInfos";
        Cache cache = cacheManager.getCache(CacheConstants.BRANCH_CACHE);
        Query query = new Query();

        List<String> allowedBuShortNames = securityRepoUtil.getCurrentUserBuIds();
        if (cache != null) {
            List<BUInfoDto> cached = cache.get(CACHE_KEY, List.class);
            if (cached != null) {
                return cached;
            }
        }
        query.addCriteria(Criteria.where("active").is(true));

        List<BranchUnit> branchUnits = mongoTemplate.find(query, BranchUnit.class);
        return basementMapper.toDtoList(branchUnits);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('HR_MANAGER') or hasRole('HR_EMPLOYEE') or hasRole('FINANCE_MANAGER') or hasRole('FINANCE_EMPLOYEE')")
    public BasePageResponse<BUInfoDto> getAllBU(FilterWithPagination filter) {
        String CACHE_KEY  = "all_buCollection_list";
        Cache cache = cacheManager.getCache(CacheConstants.BRANCH_CACHE);
        List<String> allowedBuShortNames = securityRepoUtil.getCurrentUserBuIds();

        if (allowedBuShortNames == null || allowedBuShortNames.isEmpty()) {
            throw new ResourceNotFoundException("BranchUnit", "BU short names", "No BU access for current user");
        }

        List<BUInfoDto> fullBuList = cache != null ? cache.get(CACHE_KEY, List.class) : null;

//        if (fullBuList == null) {
////            fullBuList = fetchDataFromDbAndMap();
//            if (cache != null && !fullBuList.isEmpty()) {
//                cache.put(CACHE_KEY, fullBuList);
//            }
//        }

        Page<BranchUnit> branchUnits = appUtil.buildPageResponse(filter.getFilters(),new ArrayList<>(), filter.getPagination(), BranchUnit.class);
        return buildPageResponse(branchUnits);
    }

    @Override
    public BasePageResponse<BUInfoDto> buildPageResponse(Page<BranchUnit> page) {
        List<BUInfoDto> buInfoDtoList = page.getContent().stream()
                .map(basementMapper::toDto)
                .toList();
        Pageable pageable = page.getPageable();
        return new BasePageResponse<>(
                buInfoDtoList,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }


//    private List<BUInfoDto> fetchDataFromDbAndMap() {
//        List<BranchUnit> allBu = mongoTemplate.findAll(BranchUnit.class);
//        List<BUInfoDto> buInfoDtoList = basementMapper.toDtoList(allBu);
//
//        List<String> allShortNames = buInfoDtoList.stream()
//                .map(BUInfoDto::getBuId)
//                .distinct()
//                .toList();
//
//        List<RoomInfoDto> rooms = roomCollectionService.getRoomByBuShortNameIn(allShortNames);
//        List<PriceRecordModel> prices = priceRecordRepository.findByBuShortNameIn(allShortNames);
//
//        Map<String, List<RoomInfoDto>> roomsByBu = rooms.stream()
//                .collect(Collectors.groupingBy(RoomInfoDto::getBuShortName));
//
//        Map<String, PriceRecordModel> priceByBu = prices.stream()
//                .collect(Collectors.toMap(
//                        PriceRecordModel::getBuShortName,
//                        p -> p,
//                        (existing, replacement) -> existing
//                ));
//
//        // Mapping logic
//        for (BUInfoDto buDto : buInfoDtoList) {
//            buDto.setRoomInfoDtoList(roomsByBu.getOrDefault(buDto.getBuShortName(), new ArrayList<>()));
//
//            PriceRecordModel price = priceByBu.get(buDto.getBuShortName());
//            if (price != null) {
//                buDto.setValuePerSquareMeter(price.getValuePerSize());
//                buDto.setHotDeskPrice(price.getHotDeskPrice());
//            }
//        }
//
//        return buInfoDtoList;
//    }

    @Override
    @CacheEvict(value = CacheConstants.BRANCH_CACHE, allEntries = true)
    public BUInfoDto updateBUById(String id, BUPatchRequestDto patchRequestDto) {
        BranchUnit branchUnit = branchUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BranchUnit", "_id", id));

        basementMapper.updateBasementFromDto(patchRequestDto, branchUnit);
        BranchUnit updatedItem = branchUnitRepository.save(branchUnit);

        return basementMapper.toDto(updatedItem);
    }

    @Override
    public String deleteBUById(String id) {
        branchUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BranchUnit", "_id", id));

        branchUnitRepository.deleteById(id);

        return "Delete successfully!";
    }

    @Override
    public <T> Map<T, BUInfoDto> getBatchBuIds(List<T> inputs, Function<T, String> idExtractor) {
        if (inputs == null || inputs.isEmpty()) {
            return Map.of();
        }
        Cache cache = cacheManager.getCache(CacheConstants.BRANCH_CACHE);

        List<String> allIds = inputs.stream()
                .map(idExtractor)
                .distinct()
                .toList();
        Map<String, BUInfoDto> infoDtoMap = new HashMap<>();
        List<String> missingIds = new ArrayList<>();


        for (String id : allIds) {
            assert cache != null;
            BUInfoDto cached = cache.get(id, BUInfoDto.class);
            if (cached != null) {
                infoDtoMap.put(id, cached);
            } else  {
                missingIds.add(id);
            }
        }

        if (!missingIds.isEmpty()) {
            List<BranchUnit> entities = branchUnitRepository.findAllByBuIdIn(missingIds);
            Map<String, BUInfoDto> freshData = entities.stream()
                    .collect(Collectors.toMap(BranchUnit::getBuId, basementMapper::toDto));
            freshData.forEach((id, dto) -> {
                cache.put(id, dto);
                infoDtoMap.put(id, dto);
            });
        }

        return inputs.stream().collect(Collectors.toMap(
                input -> input,
                input -> infoDtoMap.get(idExtractor.apply(input))
        ));
    }

    @Override
    public Map<String, Object> getBatchBuFullNames(List<String> buShortNames) {
        return appUtil.mapIdsToField("buCollection", "buShortName",  buShortNames,"buFullName");
    }
}
