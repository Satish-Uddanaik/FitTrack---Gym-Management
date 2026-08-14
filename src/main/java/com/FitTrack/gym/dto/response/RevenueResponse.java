package com.FitTrack.gym.dto.response;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RevenueResponse {

    private BigDecimal totalRevenue;

    private BigDecimal monthlyRevenue;

}