package com.example.demo01.domains.mongo.MiniCrm.Invoice.mapper;

import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoiceInfo;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoicePatchRequest;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoiceRequest;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    Invoice toEntity(InvoiceRequest request);
    InvoiceInfo toDto(Invoice invoice);

    InvoiceInfo toDtoFromRequest(InvoiceRequest invoiceRequest);

    List<InvoiceInfo> toDtoList(List<Invoice> invoiceList);
    void updateInvoiceFromDto(InvoicePatchRequest patchRequest, @MappingTarget Invoice invoice);

}
