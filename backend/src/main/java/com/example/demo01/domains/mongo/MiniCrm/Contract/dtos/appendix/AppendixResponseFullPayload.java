package com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix;

import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.mongo.MiniCrm.Renting.dtos.RentingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppendixResponseFullPayload {
    private ContractDTO contractDTO;
    private AppendixInfoDto appendixInfoDto;
    private List<PaymentCycleDTO> paymentCycleDTOList;
    private List<RentingDto> rentingDtoList;
    private List<TransactionInfoDTO> transactionList;
}
