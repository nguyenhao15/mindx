package com.example.demo01.domains.mongo.MiniCrm.Dashboard.service;

import com.example.demo01.domains.mongo.MiniCrm.Dashboard.dto.BranchRevenueDto;
import com.example.demo01.domains.mongo.MiniCrm.Dashboard.dto.DashboardOverviewDto;
import com.example.demo01.domains.mongo.MiniCrm.Dashboard.dto.TimeSeriesRevenue;
import com.mongodb.internal.connection.Time;

public interface DashboardService {
    DashboardOverviewDto getDashboardOverview();

    BranchRevenueDto getBranchRevenue();

    TimeSeriesRevenue getTimeSeriesRevenue( String granularity, Time startTime, Time endTime );
}
