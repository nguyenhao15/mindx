package com.example.demo01.domains.MiniCrm.Dashboard.service;

import com.example.demo01.domains.MiniCrm.Contract.models.Contract;
import com.example.demo01.domains.MiniCrm.Contract.repository.PaymentCycleRepository;
import com.example.demo01.domains.MiniCrm.Dashboard.dto.BranchRevenueDto;
import com.example.demo01.domains.MiniCrm.Dashboard.dto.DashboardOverviewDto;
import com.example.demo01.domains.MiniCrm.Dashboard.dto.TimeSeriesRevenue;
import com.example.demo01.domains.MiniCrm.Payment.repository.TransactionRepository;
import com.example.demo01.domains.MiniCrm.Renting.model.RentingRecord;
import com.example.demo01.utils.AppUtil;
import com.mongodb.internal.connection.Time;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements  DashboardService {

    private final TransactionRepository transactionRepository;

    private final PaymentCycleRepository paymentCycleRepository;

    private final MongoTemplate mongoTemplate;
    private final AppUtil appUtil;

    private int getActiveRoomCount() {
        // Implement logic to get active room count
        Criteria criteria = Criteria.where("active").is(true);
        Query query = new Query(criteria);
        long count = mongoTemplate.count(query, RentingRecord.class);
        return (int) count;
    }

    private int getActiveContractCount() {
        // Implement logic to get active contract count
        Criteria criteria = Criteria
                .where("contractStatus").is("Active")
                .and("contractDate").gte(LocalDate.now());
        Query query = new Query(criteria);
        return (int) mongoTemplate.count(query, Contract.class);
    }

    private int getDueContract() {
        Criteria criteria = Criteria
                .where("endDate").lte(LocalDate.now().plusDays(7))
                .and("contractStatus").is("Active");
        Query query = new Query(criteria);
        return (int) mongoTemplate.count(query, Contract.class);
    }

    @Override
    public DashboardOverviewDto getDashboardOverview() {
        DashboardOverviewDto dashboardOverviewDto = new DashboardOverviewDto();

        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());

        LocalDate startOfLastMonth = startOfMonth.minusMonths(1);
        LocalDate endOfLastMonthSamePeriod = today.minusMonths(1);


        Double revenue = transactionRepository.getRevenueBetween(startOfMonth, today );
        Double lastMonthRevenue = transactionRepository.getRevenueBetween(startOfLastMonth,endOfLastMonthSamePeriod);

        BigDecimal currentRevenue = BigDecimal.valueOf(revenue);
        BigDecimal lastMonthRevenueBigDe = BigDecimal.valueOf(lastMonthRevenue);

        BigDecimal growthRate = appUtil.calculateGrowthBetweenTwoValue(currentRevenue, lastMonthRevenueBigDe);

        int totalActiveRoom = getActiveRoomCount();
        int totalActiveContracts = getActiveContractCount();
//        TODO: Get KPI, growth rate

//        TODO: Get due amount, due ticket, due contract
        int totalDueContract = getDueContract();

        Double totalDueAmount = paymentCycleRepository.getTotalDueAmountForDashBoard(today);

        dashboardOverviewDto.setDueContract(totalDueContract);
        dashboardOverviewDto.setDueAmount(totalDueAmount);
        dashboardOverviewDto.setRateWithLastMonth(growthRate);
        dashboardOverviewDto.setTotalRevenue(revenue);
        dashboardOverviewDto.setActiveRoom(totalActiveRoom);
        dashboardOverviewDto.setActiveContract(totalActiveContracts);
        return dashboardOverviewDto;
    }

    @Override
    public BranchRevenueDto getBranchRevenue() {
        return null;
    }

    @Override
    public TimeSeriesRevenue getTimeSeriesRevenue(String granularity, Time startTime, Time endTime) {
        return null;
    }
}
