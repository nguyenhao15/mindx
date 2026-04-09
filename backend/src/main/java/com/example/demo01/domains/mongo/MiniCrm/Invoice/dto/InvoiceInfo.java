package com.example.demo01.domains.mongo.MiniCrm.Invoice.dto;

import com.example.demo01.domains.mongo.MiniCrm.Invoice.model.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceInfo {

    private String _id;
    private String invoiceId;


    private LocalDate invoiceDate;

    private Boolean isPaid;
    private InvoiceStatus exportStatus;
    private Boolean active;
    private String invoiceTag;

    private String invoiceNote;

    private String cycleId;
    private String contractId;
    private String customerId;
    private String buId;
    private String serviceId;

    private String customerName;
    private String buName;
    private String productName;

    private Boolean verifyInfo;

    private String appendixCode;

    private LocalDate issueDate;
    private String invoiceNumber;
    private String serialNumber;
    private String invoiceLink;
    private Double invoiceValue;

    private Instant createdDate;
    private String createdBy;

    private String lastModifiedBy;
    private Instant lastModifiedDate;
    protected Long version;

}
