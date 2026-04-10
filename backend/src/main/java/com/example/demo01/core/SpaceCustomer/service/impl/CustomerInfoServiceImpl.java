package com.example.demo01.core.SpaceCustomer.service.impl;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.core.SpaceCustomer.mapper.CustomerInfoMapper;
import com.example.demo01.core.SpaceCustomer.models.CustomerAddress;
import com.example.demo01.core.SpaceCustomer.models.CustomerContact;
import com.example.demo01.core.SpaceCustomer.models.CustomerInfo;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerInfoDTO;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerPatchRequestDTO;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerRequestDTO;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerResponse;
import com.example.demo01.repository.mongo.CoreRepo.SpaceCustomerRepository.CustomerInfoRepository;
import com.example.demo01.core.SpaceCustomer.service.CustomerInfoService;
import com.example.demo01.core.Exceptions.APIException;
import com.example.demo01.core.Exceptions.DuplicateResourceException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.utils.AppUtil;
import com.example.demo01.utils.Query.Mongo.DynamicQueryCriteria;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerInfoServiceImpl implements CustomerInfoService {

    private final CustomerInfoRepository customerInfoRepository;

    private final CustomerInfoMapper customerInfoMapper;

    private final CacheManager cacheManager;

    private final MongoTemplate mongoTemplate;

    private final AppUtil appUtil;

    private final DynamicQueryCriteria dynamicQueryCriteria;

    @Override
    public Boolean validCustomerInfo(String customerCode) {
        Boolean isExist = customerInfoRepository.existsByCustomerTaxCode(customerCode);
        if (isExist) {
            throw new DuplicateResourceException("Customer with code " + customerCode + " already exists.");
        }
        return false;
    }

    @Override
//    @Transactional
    @CacheEvict(value = CacheConstants.CUSTOMER_CACHE, key = "'customer_id_' + #id")
    public CustomerInfoDTO createCustomer(CustomerRequestDTO requestDTO) {
        try {
            CustomerInfo customerInfo = new CustomerInfo();

            List<CustomerContact> contactDTOSList = new ArrayList<>();
            List<CustomerAddress> customerAddressList = new ArrayList<>();

            String customerCodeGen = "SPACE-" + requestDTO.getCustomerTaxCode();

            customerInfo.setCustomerCode(customerCodeGen);
            customerInfo.setCustomerTitle(requestDTO.getCustomerTitle());
            customerInfo.setCustomerTaxCode(requestDTO.getCustomerTaxCode());
            customerInfo.setRepresentativeId(requestDTO.getRepresentativeId());
            customerInfo.setCustomerField(requestDTO.getCustomerField());
            customerInfo.setIsCompany(requestDTO.getIsCompany());
            contactDTOSList.add(
                    new CustomerContact(
                            "phone",
                            requestDTO.getPhone()
                    )
            );
            contactDTOSList.add(
                    new CustomerContact(
                            "email",
                            requestDTO.getEmail()
                    )
            );

            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setAddressLine(requestDTO.getAddressLine());
            customerAddress.setCity(requestDTO.getCity());
            customerAddress.setWard(requestDTO.getWard());
            customerAddressList.add(customerAddress);

            customerInfo.setContactInfo(contactDTOSList);
            customerInfo.setAddressLine(customerAddressList);

            CustomerInfo createdCustomer = customerInfoRepository.save(customerInfo);
            return customerInfoMapper.toDTO(createdCustomer);
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = CacheConstants.CUSTOMER_CACHE, key = "'customer_id_' + #id")
    public CustomerInfoDTO updateCustomerContact(String id, CustomerContact newValue) {
        String uid = newValue.getUid();
        Query query;
        Update update;
        String updateType = newValue.getUpdateType();

        if (Objects.equals(updateType, "update")) {
             query = new Query(Criteria.where("_id").is(id)
                    .and("contactInfo.uid").is(uid));
             update = new Update().set("contactInfo.$", newValue);
        } else  {
            query = new Query(Criteria.where("_id").is(id));
            update = new Update().push("contactInfo", newValue);
        }

        mongoTemplate.updateFirst(query, update, CustomerInfo.class);

        CustomerInfo updatedCustomerInfo = mongoTemplate.findById(id, CustomerInfo.class);
        return customerInfoMapper.toDTO(updatedCustomerInfo);
    }

    @Override
    @CacheEvict(value = CacheConstants.CUSTOMER_CACHE, key = "'customer_id_' + #id")
    public CustomerInfoDTO updateCustomerAddress(String id, CustomerAddress customerAddress) {
        customerInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerInfo", "id", id));

        String uid = customerAddress.getUid();
        Query query;
        Update update;
        String updateType = customerAddress.getUpdateType();

        if (updateType.equals("update")) {
             query = new Query(Criteria.where("_id").is(id)
                    .and("addressLine.uid").is(uid));
             update = new Update().set("addressLine.$", customerAddress);
        } else  {
            query = new Query(Criteria.where("_id").is(id));
            update = new Update().push("addressLine", customerAddress);
        }

        mongoTemplate.updateFirst(query, update, CustomerInfo.class);
        CustomerInfo updatedCustomerInfo = mongoTemplate.findById(id, CustomerInfo.class);
        return customerInfoMapper.toDTO(updatedCustomerInfo);
    }

    @Override
    @CacheEvict(value = CacheConstants.CUSTOMER_CACHE, key = "'customer_id_' + #id")
    public CustomerInfoDTO deleteCustomerContact(String id, String uuid) {
        // 1. Query: Chỉ cần tìm đúng khách hàng (Không cần tìm addressUuid ở đây)
        Query query = new Query(Criteria.where("_id").is(id));

        // 2. Update: Dùng pull để "lôi" phần tử ra khỏi mảng
        // Ý nghĩa: "Vào mảng addressLine, tìm item nào có uuid trùng với addressUuid thì xóa đi"
        Update update = new Update().pull("contactInfo", new BasicDBObject("uid", uuid));

        // 3. Thực thi
        mongoTemplate.updateFirst(query, update, CustomerInfo.class);

        // 4. Trả về kết quả mới
        CustomerInfo updatedCustomerInfo = mongoTemplate.findById(id, CustomerInfo.class);
        return customerInfoMapper.toDTO(updatedCustomerInfo);
    }

    @Override
    @CacheEvict(value = CacheConstants.CUSTOMER_CACHE, key = "'customer_id_' + #id")
    public CustomerInfoDTO deleteCustomerAddress(String id, String uuid) {
        Query query = new Query(Criteria.where("_id").is(id));

        Update update = new Update().pull("addressLine", Query.query(Criteria.where("uid").is(uuid)));

        try {
            UpdateResult updateResult = mongoTemplate.updateFirst(query, update, CustomerInfo.class);
            if (updateResult.getMatchedCount() == 0) {
                throw new ResourceNotFoundException("CustomerInfo", "id", id);
            }
        } catch (Exception e) {
            throw new APIException("Không thể xóa địa chỉ, vui lòng thử lại sau.");
        }

        CustomerInfo updatedCustomerInfo = mongoTemplate.findById(id, CustomerInfo.class);
        return customerInfoMapper.toDTO(updatedCustomerInfo);
    }


    //    Find customer
    @Override
    public ResponseEntity<CustomerInfoDTO> findCustomerByCustomerCode(String customerCode) {
        CustomerInfo customerItem = customerInfoRepository.findByCustomerCodeAndIsDeletedFalse(customerCode);
        if (customerItem == null)
            throw new ResourceNotFoundException("CustomerInfo", "customerCode", customerCode);

        CustomerInfoDTO dto = customerInfoMapper.toDTO(customerItem);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    @Cacheable(value = CacheConstants.CUSTOMER_CACHE, key = "'customer_id_' + #id")
    public CustomerInfoDTO findCustomerById(String id) {
        CustomerInfo customerInfo = customerInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerInfo", "id", id));

        return customerInfoMapper.toDTO(customerInfo);
    }

    @Override
    public CustomerResponse getAllCustomer(Integer pageNumber, Integer pagSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pagSize, sortByAndOrder);
        Page<CustomerInfo> customerPageItem = customerInfoRepository.findAll(pageDetails);

        List<CustomerInfo> customerResult = customerPageItem.getContent();
        List<CustomerInfoDTO> customerInfoDTOS = customerInfoMapper.toDTOList(customerResult);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setContent(customerInfoDTOS);
        customerResponse.setPageNumber(customerPageItem.getNumber());
        customerResponse.setPageSize(customerPageItem.getSize());
        customerResponse.setTotalElements(customerPageItem.getTotalElements());
        customerResponse.setTotalPages(customerPageItem.getTotalPages());
        customerResponse.setLastPage(customerPageItem.isLast());

        return customerResponse;
    }

    @Override
    public CustomerResponse getAllCustomerByTittle(String customerTitle, Integer pageNumber, Integer pagSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pagSize, sortByAndOrder);
        Page<CustomerInfo> customerInfoPage = customerInfoRepository.findByCustomerTitleContainingIgnoreCaseAndIsDeletedFalse(customerTitle, pageDetails);

        List<CustomerInfo> customerInfoList = customerInfoPage.getContent();
        List<CustomerInfoDTO> customerInfoDTOS = customerInfoMapper.toDTOList(customerInfoList);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setContent(customerInfoDTOS);
        customerResponse.setPageNumber(customerInfoPage.getNumber());
        customerResponse.setPageSize(customerInfoPage.getSize());
        customerResponse.setTotalElements(customerInfoPage.getTotalElements());
        customerResponse.setTotalPages(customerInfoPage.getTotalPages());
        customerResponse.setLastPage(customerInfoPage.isLast());

        return customerResponse;

    }

    @Override
    public CustomerInfoDTO updateCustomer(String id, CustomerPatchRequestDTO patchRequestDTO) {
        CustomerInfo customerInfo = customerInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerInfo", "_id", id));

        customerInfoMapper.updateCustomerFromDto(patchRequestDTO, customerInfo);

        CustomerInfo updatedCustomerItem = customerInfoRepository.save(customerInfo);

        return customerInfoMapper.toDTO(updatedCustomerItem);
    }

    @Override
    public String deleteCustomerRelevantInfo(String id) {
        CustomerInfo customerInfo = customerInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerInfo", "_id", id));

        customerInfo.setIsDeleted(true);
        customerInfoRepository.save(customerInfo);

        return "Deleted relevant customer info successfully!";
    }

    @Override
    public List<CustomerInfoDTO> searchCustomers(String keyword) {
        Query query = new Query();

        if (keyword != null && !keyword.isEmpty()) {
            Criteria searchCriteria = new Criteria().orOperator(
                    Criteria.where("customerCode").regex(keyword, "i"),
                    Criteria.where("customerTitle").regex(keyword, "i"),
                    Criteria.where("customerTaxCode").regex(keyword, "i")
            );
            query.addCriteria(searchCriteria);
        }

        return mongoTemplate.find(query, CustomerInfo.class)
                .stream()
                .map(customerInfoMapper::toDTO)
                .toList();
    }

    @Override
    public <T> Map<T, CustomerInfoDTO> getBatchCustomers(List<T> inputs, Function<T, String> idExtractor) {
        if (inputs == null || inputs.isEmpty()) {
            return Map.of();
        }

        Cache cache = cacheManager.getCache("customer_details");

        List<String> allIds = inputs.stream()
                .map(idExtractor)
                .distinct()
                .toList();
        Map<String, CustomerInfoDTO> infoDtoMap = new HashMap<>();
        List<String> missingIds = new ArrayList<>();


        for (String id : allIds) {
            assert cache != null;
            CustomerInfoDTO cached = cache.get(id, CustomerInfoDTO.class);
            if (cached != null) {
                infoDtoMap.put(id, cached);
            } else  {
                missingIds.add(id);
            }
        }


        if (!missingIds.isEmpty()) {
            List<CustomerInfo> entities = customerInfoRepository.findAllById(missingIds);

            Map<String, CustomerInfoDTO> freshData = entities.stream()
                    .collect(Collectors.toMap(CustomerInfo::get_id, customerInfoMapper::toDTO));
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
    public Map<String, Object> getBatchCustomerTitle(List<String> customerIds) {
        return dynamicQueryCriteria.mapIdsToField("CustomerInfo", "_id", customerIds, "customerTitle");
    }

}
