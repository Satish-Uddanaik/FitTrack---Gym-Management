package com.FitTrack.gym.Service;

import com.FitTrack.gym.dto.response.DashboardReportResponse;
import com.FitTrack.gym.dto.response.MemberStatusResponse;
import com.FitTrack.gym.dto.response.MembershipStatsResponse;
import com.FitTrack.gym.dto.response.RevenueResponse;

import java.util.List;

public interface ReportService {

    DashboardReportResponse getDashboardReport();

    List<MemberStatusResponse> getMemberStatus();

    List<MembershipStatsResponse> getMembershipStats();

    RevenueResponse getRevenue();

}