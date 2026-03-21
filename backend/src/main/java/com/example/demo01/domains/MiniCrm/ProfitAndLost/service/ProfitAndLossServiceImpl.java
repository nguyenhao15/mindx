package com.example.demo01.domains.MiniCrm.ProfitAndLost.service;

import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.MiniCrm.ProfitAndLost.dtos.AllocationObject;
import com.example.demo01.domains.MiniCrm.ProfitAndLost.model.ProfitAndLost;
import com.example.demo01.domains.MiniCrm.ProfitAndLost.repository.ProfitAndLostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfitAndLossServiceImpl implements ProfitAndLossService {

    private final ProfitAndLostRepository profitAndLostRepository;

    @Override
    public void handleAllocate(AllocationObject allocationObject) {

        LocalDate startDate = allocationObject.getBeginAllocationDate();
        Integer usingDays = allocationObject.getTotalUsingDays();
        Double paymentPerMonth = allocationObject.getAllocationPerLoop();

        Double totalAllocateAmount = allocationObject.getAllocationTotalAmount();
//        Total amount not yet allocated
        Double totalPaidAmount = allocationObject.getTotalPaidAmount();
        Double exchangeRateValue = allocationObject.getExchangeRate();

        String contractId = allocationObject.getUsingId();
        String buId = allocationObject.getBuId();
        String usingId = allocationObject.getUsingId();
        String serviceShortName = allocationObject.getServiceId();
        String paymentCycleId = allocationObject.getPaymentCycleId();
        List<ProfitAndLost> pnlItems = new ArrayList<>();
        String invoiceId = allocationObject.getInvoiceId();

        boolean isNonContract = contractId == null || contractId.isEmpty();

        if (isNonContract && invoiceId != null && !invoiceId.isEmpty()) {
            ProfitAndLost profitAndLost = new ProfitAndLost();
            profitAndLost.setTrackId(paymentCycleId);
            profitAndLost.setRemainAmount(0.0);
            profitAndLost.setAllocatedAmount(totalPaidAmount);
            profitAndLost.setLocalAmount(totalAllocateAmount * exchangeRateValue);
            profitAndLost.setAllocationDate(startDate);
            profitAndLost.setBuId(buId);
            profitAndLost.setServiceId(serviceShortName);
            profitAndLost.setTrackId(invoiceId);
            profitAndLost.setUsingDays(usingDays);

            profitAndLostRepository.save(profitAndLost);
            pnlItems.add(profitAndLost);
            return;
        }
        List<ProfitAndLost> pnlByPaymentCycle = profitAndLostRepository.findByTrackIdAndRemainAmountGreaterThan(paymentCycleId, 0.0);
        double remainingValueToAllocate = totalPaidAmount;

        if (!pnlByPaymentCycle.isEmpty()) {
            for (ProfitAndLost allocateItem : pnlByPaymentCycle) {

                if (remainingValueToAllocate == 0 ) break;

                double remainAmountThisItem = allocateItem.getRemainAmount();

                double updateAmountValue = Math.min(remainAmountThisItem, remainingValueToAllocate);

                double updateAllocatedAmount = allocateItem.getAllocatedAmount() + updateAmountValue * exchangeRateValue;
                double remainAllocateItem = allocateItem.getRemainAmount() - updateAmountValue;

                remainingValueToAllocate -= updateAmountValue;
                allocateItem.setAllocatedAmount(updateAllocatedAmount);
                allocateItem.setLocalAmount(updateAllocatedAmount);
                allocateItem.setRemainAmount(remainAllocateItem);
            }
            profitAndLostRepository.saveAll(pnlByPaymentCycle);
            return;
        }

        int remainingDaysNeedToAlloc = usingDays;
        double remainingAllocateAmount = totalAllocateAmount;
        LocalDate currentLoopDate = startDate;

        boolean isFirstLoop = true;

        int daysInMonth = startDate.lengthOfMonth();
        int daysRemaining = daysInMonth - startDate.getDayOfMonth() + 1;
        double usingRateAtFirstMonth = (double) daysRemaining / daysInMonth;

        while (remainingDaysNeedToAlloc > 0 && remainingAllocateAmount > 0) {

            LocalDate thisStartOfDate = isFirstLoop ? currentLoopDate : currentLoopDate.withDayOfMonth(1);

            LocalDate endOfMonth = currentLoopDate.with(TemporalAdjusters.lastDayOfMonth());

            int maxDaysInThis = (int) ChronoUnit.DAYS.between(thisStartOfDate, endOfMonth.plusDays(1));

            int daysAllocatedInThisLoop = Math.min(remainingDaysNeedToAlloc, maxDaysInThis);
            boolean isLastLoop = (remainingDaysNeedToAlloc - daysAllocatedInThisLoop) == 0;

            double calculatedThisLoop;
            calculatedThisLoop = isLastLoop
                    ? remainingAllocateAmount
                    : isFirstLoop
                    ? Math.round(paymentPerMonth * usingRateAtFirstMonth)
                    : Math.round(paymentPerMonth);


            double updatePaidAmountThisLoop = remainingValueToAllocate > 0
                    ? Math.min(calculatedThisLoop, remainingValueToAllocate)
                    : 0;

            ProfitAndLost thisMonthPnl = new ProfitAndLost();

            thisMonthPnl.setAllocationDate(thisStartOfDate);
            thisMonthPnl.setTotalAllocationAmount(calculatedThisLoop);
            thisMonthPnl.setAllocatedAmount(updatePaidAmountThisLoop);
            thisMonthPnl.setLocalAmount(updatePaidAmountThisLoop * exchangeRateValue);
            thisMonthPnl.setUsingDays(daysAllocatedInThisLoop);
            thisMonthPnl.setRemainAmount(calculatedThisLoop - updatePaidAmountThisLoop);

            thisMonthPnl.setBuId(buId);
            thisMonthPnl.setContractId(usingId);
            thisMonthPnl.setServiceId(serviceShortName);
            thisMonthPnl.setTrackId(paymentCycleId);

            pnlItems.add(thisMonthPnl);

            remainingAllocateAmount -= calculatedThisLoop;
            remainingDaysNeedToAlloc -= daysAllocatedInThisLoop;
            remainingValueToAllocate -= updatePaidAmountThisLoop;

            currentLoopDate = currentLoopDate.plusMonths(1).withDayOfMonth(1);
            isFirstLoop = false;
        }
        profitAndLostRepository.saveAll(pnlItems);
    }

    @Override
    public void deleteAllByAppendixCode(String appendixCode) {
        profitAndLostRepository.deleteByAppendixCode(appendixCode);
    }

    @Override
    public AllocationObject getAllocationObject(Double paidAmount, Double exchangeRate, PaymentCycleDTO item) {
        AllocationObject allocationObject = new AllocationObject();
        allocationObject.setServiceId(item.getServiceId());
        allocationObject.setUsingId(item.getContractId());
        allocationObject.setAllocationPerLoop(item.getMonthlyAmount());
        allocationObject.setAllocationTotalAmount(item.getTotalAmount());
        allocationObject.setTotalPaidAmount(paidAmount);
        allocationObject.setBeginAllocationDate(item.getCycleDueDate());
        allocationObject.setTotalUsingDays(item.getDays());
        allocationObject.setExchangeRate(exchangeRate);
        allocationObject.setPaymentCycleId(item.getCycleId());
        allocationObject.setBuId(item.getBuId());
        return allocationObject;
    }

    @Override
    public void handleDeallocate(AllocationObject allocationObject, String cycleId) {
        List<ProfitAndLost> pnlByPaymentCycle = profitAndLostRepository.findByTrackId(cycleId);
        double deallocateAmount = allocationObject.getTotalPaidAmount();
        pnlByPaymentCycle.sort(Comparator.comparing(ProfitAndLost::getAllocationDate).reversed());

        for (ProfitAndLost pnlItem : pnlByPaymentCycle) {
            if (deallocateAmount == 0) break;

            double allocatedAmountThisItem = pnlItem.getAllocatedAmount();
            double updateDeallocateValue = Math.min(allocatedAmountThisItem, deallocateAmount);

            double updatedAllocatedAmount = allocatedAmountThisItem - updateDeallocateValue;
            double updatedRemainAmount = pnlItem.getRemainAmount() + updateDeallocateValue;

            deallocateAmount -= updateDeallocateValue;

            pnlItem.setAllocatedAmount(updatedAllocatedAmount);
            pnlItem.setRemainAmount(updatedRemainAmount);
        }
        profitAndLostRepository.saveAll(pnlByPaymentCycle);
    }

    @Override
    public void updateExistItem(AllocationObject allocationObject, String cycleId) {
        List<ProfitAndLost> thisPnlItem = profitAndLostRepository.findByTrackId(cycleId);
        double newTotalPaidAmount = allocationObject.getTotalPaidAmount();
        for (ProfitAndLost pnlItem : thisPnlItem) {
            double allocatedAmountThisItem = pnlItem.getAllocatedAmount();
            double remainAmountThisItem = pnlItem.getRemainAmount();

            double totalCurrentAmount = allocatedAmountThisItem + remainAmountThisItem;

            if (newTotalPaidAmount >= totalCurrentAmount) {
                pnlItem.setAllocatedAmount(totalCurrentAmount);
                pnlItem.setRemainAmount(0.0);
                newTotalPaidAmount -= totalCurrentAmount;
            } else {
                pnlItem.setAllocatedAmount(newTotalPaidAmount);
                pnlItem.setRemainAmount(totalCurrentAmount - newTotalPaidAmount);
                newTotalPaidAmount = 0.0;
            }
        }
    }

}
