package com.example.demo01.domains.MiniCrm.Dashboard.service;

import com.example.demo01.domains.MiniCrm.Dashboard.dto.BranchRevenueDto;
import com.example.demo01.domains.MiniCrm.Dashboard.dto.DashboardOverviewDto;
import com.example.demo01.domains.MiniCrm.Dashboard.dto.TimeSeriesRevenue;
import com.mongodb.internal.connection.Time;

public interface DashboardService {
    DashboardOverviewDto getDashboardOverview();

    BranchRevenueDto getBranchRevenue();

    TimeSeriesRevenue getTimeSeriesRevenue( String granularity, Time startTime, Time endTime );
}
