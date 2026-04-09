package com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.paymentOrder;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentOrderDTO {

    private String _id;
    private Double paymentAmount;
    private Double exchangeRate;
    private Double localAmount;

    private String currencyCode;

    private String paymentMethod;
    private LocalDate paymentDate;

    private String customerId;

    private String isConfirmed;

    private List<FileResponseDTO> attachments;
}
