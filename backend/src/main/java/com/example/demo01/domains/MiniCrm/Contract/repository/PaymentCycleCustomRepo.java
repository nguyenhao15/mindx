package com.example.demo01.domains.MiniCrm.Contract.repository;

import java.time.LocalDate;

public interface PaymentCycleCustomRepo {
    Double getTotalDueAmountForDashBoard(LocalDate date);
}
