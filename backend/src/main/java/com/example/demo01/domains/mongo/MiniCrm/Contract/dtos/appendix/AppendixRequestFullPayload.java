package com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix;

import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractRequestDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppendixRequestFullPayload {


    private ContractRequestDto contractRequest;

    @NotNull
    private AppendixRequest appendixRequest;

    private List<PaymentCycleDTO> paymentCycleDTOList;

}
