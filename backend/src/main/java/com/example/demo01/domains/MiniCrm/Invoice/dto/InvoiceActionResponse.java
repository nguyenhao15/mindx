package com.example.demo01.domains.MiniCrm.Invoice.dto;

public record InvoiceActionResponse(
    String nextStatus, 
    String label, 
    String actionType // Dùng để CSS ở Frontend
) {}