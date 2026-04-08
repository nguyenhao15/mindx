package com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository;

import java.time.LocalDate;

public interface PaymentCycleCustomRepo {
    Double getTotalDueAmountForDashBoard(LocalDate date);
}
