package com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts;

import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.mongo.MiniCrm.Renting.dtos.RentingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractFullPayload {

    ContractDTO contractInfo;

    List<AppendixInfoDto> appendixInfoList;

    List<PaymentCycleDTO> paymentCycles;

    List<RentingDto> rentals;

}
