package com.example.demo01.domains.mongo.MiniCrm.Payment.models;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "payment_order")
public class PaymentOrder {

    @Id
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

    @CreatedDate
    private Instant createdTime;

    @CreatedBy
    private String createdBy;

    @CreatedBy
    private String modifiedBy;

    @LastModifiedDate
    private Instant modifiedTime;
}
