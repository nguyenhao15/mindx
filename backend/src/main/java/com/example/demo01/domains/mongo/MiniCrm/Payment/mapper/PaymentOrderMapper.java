package com.example.demo01.domains.mongo.MiniCrm.Payment.mapper;

import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.paymentOrder.PaymentOrderDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.paymentOrder.PaymentOrderPatchRequest;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.paymentOrder.PaymentOrderRequest;
import com.example.demo01.domains.mongo.MiniCrm.Payment.models.PaymentOrder;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentOrderMapper {
    PaymentOrder toEntity(PaymentOrderRequest paymentOrderRequest);
    PaymentOrderDTO toDto(PaymentOrder paymentOrder);
    List<PaymentOrderDTO> toDtoList(List<PaymentOrder> paymentOrders);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePaymentOrderFromDtoList(PaymentOrderPatchRequest paymentOrderPatchRequest, @MappingTarget PaymentOrder paymentOrder);
}
