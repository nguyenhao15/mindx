package com.example.demo01.domains.mongo.MiniCrm.Dimmesion.service.ServiceImpl;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.model.provinceModel;
import com.example.demo01.repository.mongo.MiniCrmRepository.dimRepository.ProvinceRepository;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final
    ProvinceRepository provinceRepository;

    @Override
    @Cacheable(value = CacheConstants.PROVINCE_CACHE, key = "'all_provincesData_list'")
    public List<provinceModel> getAllProvinces() {
        return provinceRepository.findAll();
    }
}
