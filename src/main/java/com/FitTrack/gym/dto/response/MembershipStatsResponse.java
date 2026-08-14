package com.FitTrack.gym.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MembershipStatsResponse {

    private Long membershipId;

    private String planName;

    private Long memberCount;

}