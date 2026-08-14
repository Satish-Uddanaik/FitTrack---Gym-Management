package com.FitTrack.gym.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {

    private String title;

    private String message;

    private String type;

}
