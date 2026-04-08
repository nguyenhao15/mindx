package com.example.demo01.domains.MiniCrm.Dimmesion.service.ServiceImpl;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Currency.CurrencyInfoDto;
import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Currency.CurrencyPatchRequest;
import com.example.demo01.domains.MiniCrm.Dimmesion.mappers.CurrencyMapper;
import com.example.demo01.domains.MiniCrm.Dimmesion.model.CurrencyDB;
import com.example.demo01.repository.mongo.MiniCrmRepository.dimRepository.CurrencyDbRepository;
import com.example.demo01.domains.MiniCrm.Dimmesion.service.CurrencyService;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {


    private final CurrencyDbRepository currencyDbRepository;

    private final CurrencyMapper currencyMapper;

    @Override
    @Cacheable(value = CacheConstants.CURRENCY_CACHE, key = "'all_currencies_list'")
    public List<CurrencyInfoDto> getAllCurrency() {
        List<CurrencyDB> currencyDBList = currencyDbRepository.findAll();
        if (!currencyDBList.isEmpty()) {
            return currencyMapper.toDtos(currencyDBList);
        }
        return List.of();
    }

    @Override
    @Cacheable(value = CacheConstants.CURRENCY_CACHE, key = "#active")
    public List<CurrencyInfoDto> getActiveCurrency(Boolean active) {
        List<CurrencyDB> currencyDBList = currencyDbRepository.findByActive(active);
        if (!currencyDBList.isEmpty()) {
            return currencyMapper.toDtos(currencyDBList);
        }
        return List.of();
    }

    @Override
    @CacheEvict(value = CacheConstants.CURRENCY_CACHE, allEntries = true)
    public CurrencyInfoDto createNewCurrency(CurrencyPatchRequest currencyPatchRequest) {
        CurrencyDB currencyInfoDto = currencyDbRepository.save(currencyMapper.toEntity(currencyPatchRequest));
        return currencyMapper.toDto(currencyInfoDto);
    }



    @Override
    @CacheEvict(value = CacheConstants.CURRENCY_CACHE, allEntries = true)
    public CurrencyInfoDto updateCurrency(String id, CurrencyPatchRequest currencyPatchRequest) {
        CurrencyDB updateItem = currencyDbRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CurrencyDB", "id", id));
        currencyMapper.updateCurrencyFromDto(currencyPatchRequest, updateItem);
        CurrencyDB savedItem  = currencyDbRepository.save(updateItem);
        return currencyMapper.toDto(savedItem);
    }

    @Override
    @Cacheable(value = CacheConstants.CURRENCY_CACHE, key = "#id")
    public CurrencyInfoDto getCurrency(String id) {
        CurrencyDB currencyDB = currencyDbRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CurrencyDB", "id", id));
        return currencyMapper.toDto(currencyDB);
    }

    @Override
    @CacheEvict(value = CacheConstants.CURRENCY_CACHE, allEntries = true)
    public String deleteCurrency(String id) {
        CurrencyDB currencyDB = currencyDbRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CurrencyDB", "id", id));
        currencyDbRepository.delete(currencyDB);
        return "Delete currency item successfully!!";
    }
}
