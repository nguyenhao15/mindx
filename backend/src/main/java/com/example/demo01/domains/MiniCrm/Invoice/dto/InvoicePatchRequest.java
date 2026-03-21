package com.example.demo01.domains.MiniCrm.Invoice.dto;

import com.example.demo01.domains.MiniCrm.Invoice.model.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoicePatchRequest {

    private String serviceId;
    private String buId;

    private String cycleId;
    private String contractId;
    private LocalDate invoiceDate;
    private Boolean paidStatus;
    private String invoiceTag;

    private String appendixCode;

    private String updateDescription;

    private InvoiceStatus exportStatus;

    private Boolean active;

    private String customerId;

    private Boolean verifyInfo;

    private LocalDate issueDate;
    private String invoiceNumber;
    private String serialNumber;
    private String invoiceLink;
    private Double invoiceValue;
    private String invoiceNote;

}
