package com.FitTrack.gym.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private Long totalMembers;

    private Long activeMembers;

    private Long inactiveMembers;

    private Long expiredMembers;

    private Long totalMembershipPlans;

    private Long dueBillsToday;

}