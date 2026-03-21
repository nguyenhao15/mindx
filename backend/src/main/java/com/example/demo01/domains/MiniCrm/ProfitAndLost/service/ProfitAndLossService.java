package com.example.demo01.domains.MiniCrm.ProfitAndLost.service;

import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.MiniCrm.ProfitAndLost.dtos.AllocationObject;

public interface ProfitAndLossService {

    void handleAllocate(AllocationObject allocationObject);

    void deleteAllByAppendixCode(String appendixCode);

    AllocationObject getAllocationObject(Double paidAmount, Double exchangeRate, PaymentCycleDTO item);

    void handleDeallocate(AllocationObject allocationObject, String cycleId);

    void updateExistItem(AllocationObject allocationObject, String cycleId);

}
