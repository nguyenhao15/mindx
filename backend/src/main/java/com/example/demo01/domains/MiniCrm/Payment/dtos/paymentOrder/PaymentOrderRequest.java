package com.example.demo01.domains.MiniCrm.Payment.dtos.paymentOrder;


import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.TransactionRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PaymentOrderRequest {

    @Min(value = 1,message = "Payment Amount must be greater 1")
    private Double paymentAmount;

    @Min(value = 1,message = "Exchange Rate must not be blank")
    private Double exchangeRate;

    private Double localAmount;

    private String currencyCode;

    private String paymentMethod;
    private LocalDate paymentDate;

    @NotBlank(message = "Customer must not be blank")
    private String customerId;

    private String isConfirmed = "unConfirmed";

    private List<FileResponseDTO> attachments;

    private List<TransactionRequest> transactionRequests;
}
