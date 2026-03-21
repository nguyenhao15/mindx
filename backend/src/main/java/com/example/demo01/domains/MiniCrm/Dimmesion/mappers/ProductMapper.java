package com.example.demo01.domains.MiniCrm.Dimmesion.mappers;

import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Products.ProductInfoDto;
import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Products.ProductPatchRequestDto;
import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Products.ProductRequestDto;
import com.example.demo01.domains.MiniCrm.Dimmesion.model.ProductModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper{

    ProductModel toEntity(ProductRequestDto requestDto);
    ProductInfoDto toDto(ProductModel productModel);
    List<ProductInfoDto> toDTOList(List<ProductModel> productModels);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(ProductPatchRequestDto productPatchRequestDto, @MappingTarget ProductModel productModel);
}
