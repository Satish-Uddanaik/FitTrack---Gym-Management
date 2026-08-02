package com.FitTrack.gym.dto.request;



import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MembershipRequest {

    @NotBlank(message = "Plan name is required")
    private String planName;

    @Min(value = 1, message = "Duration must be at least 1 month")
    private Integer durationInMonths;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    private String description;

}
