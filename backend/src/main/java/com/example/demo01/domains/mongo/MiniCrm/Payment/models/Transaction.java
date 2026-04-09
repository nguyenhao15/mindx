package com.example.demo01.domains.mongo.MiniCrm.Payment.models;

import com.example.demo01.utils.BaseModels.BusinessUnitEntity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "transactions")
public class Transaction extends BusinessUnitEntity {

    @Id
    private String _id;

    @Indexed
    private String paymentOrderId;

    private Double amount;

    private Double depositRemainAmount;
    private LocalDate paymentDate;

    @Indexed
    private String customerId;

    private Double exchangeRate;

    private Double localAmount;

    @Indexed
    private String serviceId;

    private List<TransactionAllocate> transactionAllocates;

    //    Deposit, payment, handed
    private String paymentType;

    @Indexed
    private String invoiceId;

    //    case: payment contract
    @Indexed
    private String usingId;
}
