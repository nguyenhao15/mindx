package com.example.demo01.domains.mongo.MiniCrm.Payment.mapper;

import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionPatchRequest;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionRequest;
import com.example.demo01.domains.mongo.MiniCrm.Payment.models.Transaction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

     Transaction toEntity(TransactionRequest request);

     Transaction toEntityFromDto(TransactionInfoDTO transactionInfoDTO);

     TransactionInfoDTO toDto(Transaction transaction);

     TransactionInfoDTO fromRequestToDto(TransactionRequest transactionRequest);

     List<TransactionInfoDTO> toDTOList(List<Transaction> transactions);

     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     void updatePaymentFromDto(TransactionPatchRequest detailPatchRequest, @MappingTarget Transaction transaction);
}
