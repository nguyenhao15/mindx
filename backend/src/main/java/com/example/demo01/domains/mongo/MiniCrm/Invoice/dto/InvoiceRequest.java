package com.example.demo01.domains.mongo.MiniCrm.Invoice.dto;

import com.example.demo01.domains.mongo.MiniCrm.Invoice.model.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceRequest {

    private String invoiceId;
    private String serviceId;
    private String buId;
    private Double invoiceValue;
    private LocalDate invoiceDate;

    private Boolean isPaid;
    private InvoiceStatus exportStatus;

    private String cycleId;
    private String contractId;
    private String appendixCode;

    private String customerId;
    private Boolean active;

    private Boolean verifyInfo =true;
    private String invoiceNote;
    private String invoiceTag;

    private LocalDate issueDate;
    private String invoiceNumber;
    private String serialNumber;
    private String invoiceLink;


}
