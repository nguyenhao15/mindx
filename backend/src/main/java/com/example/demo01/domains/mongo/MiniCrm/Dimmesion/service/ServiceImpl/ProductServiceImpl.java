package com.example.demo01.domains.mongo.MiniCrm.Dimmesion.service.ServiceImpl;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.dtos.Products.ProductInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.dtos.Products.ProductRequestDto;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.mappers.ProductMapper;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.model.ProductModel;
import com.example.demo01.repository.mongo.MiniCrmRepository.dimRepository.ProductRepository;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.service.ProductService;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.utils.AppUtil;
import com.example.demo01.utils.Query.Mongo.DynamicQueryCriteria;
import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CacheManager cacheManager;

    private final DynamicQueryCriteria dynamicQueryCriteria;

    @Override
    @CacheEvict(value = CacheConstants.SERVICE_CACHE, allEntries = true)
    public ResponseEntity<?> createProduct(ProductRequestDto productRequestDto) {
        try {
            ProductModel product = productRepository.save(productMapper.toEntity(productRequestDto));
            ProductInfoDto productInfoDto = productMapper.toDto(product);
            return new ResponseEntity<>(productInfoDto, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Product short name with the same short name already exists");
        }

    }

    @Override
    @Cacheable(value = CacheConstants.SERVICE_CACHE, key = "'all_products_list'")
    public List<ProductInfoDto> getAllProduct() {
        Cache cache = cacheManager.getCache(CacheConstants.SERVICE_CACHE);
        String cacheKey = "all_products_list";
        if (cache == null) {
            return productRepository.findAll()
                    .stream()
                    .map(productMapper::toDto)
                    .collect(Collectors.toList());
        }
        // Try to return cached full list
        List<ProductInfoDto> cachedList = cache.get(cacheKey, List.class);
        if (cachedList != null) {
            return cachedList;
        }

        // Cache miss: load from DB, populate cache and return
        List<ProductModel> productModels = productRepository.findAll();
        List<ProductInfoDto> dtos = productModels.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        cache.put(CacheConstants.SERVICE_CACHE, dtos);
        return dtos;
    }

    @Override
    public ProductInfoDto getProductById(String productId) {
        String cacheKey = "getProductById_" + productId;
        Cache cache = cacheManager.getCache(CacheConstants.SERVICE_CACHE);
        if (cache != null) {
            ProductInfoDto cached = cache.get(cacheKey, ProductInfoDto.class);
            if (cached != null) {
                return cached;
            }
        }
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found", "productId", productId));
        return productMapper.toDto(product);
    }

    @Override
    public <T> Map<T, ProductInfoDto> getBatchProducts(List<T> inputs, Function<T, String> idExtractor) {
        if (inputs == null || inputs.isEmpty()) {
            return Map.of();
        }

        Cache cache = cacheManager.getCache(CacheConstants.SERVICE_CACHE);

        List<String> allIds = inputs.stream()
                .map(idExtractor)
                .distinct()
                .toList();
        Map<String, ProductInfoDto> infoDtoMap = new HashMap<>();
        List<String> missingIds = new ArrayList<>();


        for (String id : allIds) {
            assert cache != null;
            ProductInfoDto cached = cache.get(id, ProductInfoDto.class);
            if (cached != null) {
                infoDtoMap.put(id, cached);
            } else  {
                missingIds.add(id);
            }
        }


        if (!missingIds.isEmpty()) {
            List<ProductModel> entities = productRepository.findAllById(missingIds);

            Map<String, ProductInfoDto> freshData = entities.stream()
                    .collect(Collectors.toMap(ProductModel::get_id, productMapper::toDto));
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
    public Map<String, Object> getBatchByServiceName(List<String> serviceIds) {
        return dynamicQueryCriteria.mapIdsToField("products", "_id", serviceIds, "serviceName");
    }


}
