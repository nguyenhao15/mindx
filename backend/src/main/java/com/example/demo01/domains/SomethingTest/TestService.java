package com.example.demo01.domains.SomethingTest;

import com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository.AppendixRepository;
import com.example.demo01.core.SpaceCustomer.mapper.CustomerInfoMapper;
import com.example.demo01.core.SpaceCustomer.models.CustomerInfo;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerInfoDTO;
import com.example.demo01.repository.mongo.CoreRepo.SpaceCustomerRepository.CustomerInfoRepository;
import com.example.demo01.domains.MiniCrm.Dimmesion.mappers.ProductMapper;
import com.example.demo01.repository.mongo.MiniCrmRepository.dimRepository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final AppendixRepository appendixRepository;

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CustomerInfoRepository customerInfoRepository;
    private final CustomerInfoMapper customerInfoMapper;


    public Map<String, CustomerInfoDTO> getCustomerByIds(List<String> customerIds) {
        log.info("Batch fetching services for IDs: {}", customerIds);

        // MongoDB: find all by IDs
        List<CustomerInfo> entities = customerInfoRepository.findAllById(customerIds);

        return entities.stream().collect(Collectors.toMap(
                CustomerInfo::get_id,
                customerInfoMapper::toDTO
        ));
    }

}
