package com.example.demo01.core.SpaceCustomer.service;

import com.example.demo01.core.SpaceCustomer.models.CustomerAddress;
import com.example.demo01.core.SpaceCustomer.models.CustomerContact;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerInfoDTO;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerPatchRequestDTO;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerRequestDTO;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.function.Function;


public interface CustomerInfoService {

    Boolean validCustomerInfo(String customerCode);

    CustomerInfoDTO createCustomer(CustomerRequestDTO requestDTO);

    CustomerInfoDTO updateCustomerContact(String id, CustomerContact newValue);

    CustomerInfoDTO updateCustomerAddress(String id, CustomerAddress customerAddress);

    CustomerInfoDTO deleteCustomerContact(String id, String uuid);

    CustomerInfoDTO deleteCustomerAddress(String id, String uuid);

    ResponseEntity<CustomerInfoDTO> findCustomerByCustomerCode(String customerCode);

    CustomerInfoDTO findCustomerById(String id);

    CustomerResponse getAllCustomer(Integer pageNumber, Integer pagSize, String sortBy, String sortOrder);

    CustomerResponse getAllCustomerByTittle(String customerTitle, Integer pageNumber, Integer pagSize, String sortBy, String sortOrder);

    CustomerInfoDTO updateCustomer(String id, CustomerPatchRequestDTO patchRequestDTO);

    String deleteCustomerRelevantInfo(String id);

    List<CustomerInfoDTO> searchCustomers(String keyword);

    <T> Map<T, CustomerInfoDTO> getBatchCustomers(List<T> inputs, Function<T, String> idExtractor);

    Map<String, Object> getBatchCustomerTitle(List<String> customerIds);
}
