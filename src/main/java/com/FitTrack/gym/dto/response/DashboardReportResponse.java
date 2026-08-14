package com.FitTrack.gym.dto.response;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardReportResponse {

    private Long totalMembers;

    private Long activeMembers;

    private Long expiredMembers;

    private Long inactiveMembers;

    private Long totalMembershipPlans;

    private Long todayDueBills;

    private BigDecimal totalRevenue;

    private BigDecimal monthlyRevenue;

}