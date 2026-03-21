package com.example.demo01.domains.MiniCrm.Invoice.repository;

import com.example.demo01.domains.MiniCrm.Invoice.model.Invoice;

import java.util.List;

public interface InvoiceCustomRepository {

    List<Invoice> getActiveInvoicesByCustomerId(String customerId);

}
