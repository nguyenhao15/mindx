package com.example.demo01.domains.mongo.MiniCrm.Invoice.dto;

public record InvoiceActionResponse(
    String nextStatus, 
    String label, 
    String actionType // Dùng để CSS ở Frontend
) {}