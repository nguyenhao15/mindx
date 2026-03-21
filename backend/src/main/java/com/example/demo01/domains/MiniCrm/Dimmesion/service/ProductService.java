package com.example.demo01.domains.MiniCrm.Dimmesion.service;

import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Products.ProductInfoDto;
import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Products.ProductRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface ProductService {

    ResponseEntity<?> createProduct(ProductRequestDto productRequestDto);

    List<ProductInfoDto> getAllProduct();

    ProductInfoDto getProductById(String productId);

    <T> Map<T, ProductInfoDto> getBatchProducts(List<T> inputs, Function<T, String> idExtractor);

    Map<String, Object> getBatchByServiceName(List<String> serviceIds);

}
