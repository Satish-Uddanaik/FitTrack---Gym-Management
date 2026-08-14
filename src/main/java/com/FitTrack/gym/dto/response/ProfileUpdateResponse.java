package com.FitTrack.gym.dto.response;

import com.FitTrack.gym.dto.response.ProfileResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileUpdateResponse {

    private ProfileResponse profile;

    private String token;

    private String message;
}
