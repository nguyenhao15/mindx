package com.example.demo01.domains.mongo.MiniCrm.Invoice.model;

import com.example.demo01.utils.BaseEntity.Mongo.BusinessUnitEntity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "invoiceTable")
public class Invoice extends BusinessUnitEntity {

    @Id
    private String _id;

    private String invoiceId;
    private String serviceId;
    private LocalDate invoiceDate;

    private String invoiceNote;

    private Boolean isPaid;

    private String cycleId;
    private String contractId;
    private String appendixCode;

    private String customerId;
    private InvoiceStatus exportStatus;
    private Boolean active;

    private Boolean verifyInfo;

    private LocalDate issueDate;
    private String invoiceNumber;
    private String serialNumber;
    private String invoiceLink;

    private String invoiceTag;

    private Double invoiceValue;

}
