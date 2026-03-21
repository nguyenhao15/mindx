package com.example.demo01.domains.MiniCrm.Contract.models;

import com.example.demo01.utils.BusinessUnitEntity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "paymentCycles")
public class PaymentCycle extends BusinessUnitEntity {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String cycleId;

    @Indexed
    private String contractId;

    @Indexed
    private String appendixId;

    @Indexed
    private String serviceId;

    private Integer days;
    private LocalDate cycleDueDate;


    private String customerId;

    private String currencyId;

    private Double monthlyAmount;
    private Double totalAmount;
    private Double paidAmount;
    private Double remainAmount;

    @Indexed
    private Boolean exportedInvoice;

    private PaymentTermStatus status;

    @Indexed
    private Boolean active;

    @PrePersist
    @PreUpdate
    public void calculateRemaining() {
        if (this.totalAmount == null) this.totalAmount = 0.0;
        if (this.paidAmount == null) this.paidAmount = 0.0;
        this.remainAmount = this.totalAmount - this.paidAmount;
    }

}
