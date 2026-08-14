package com.FitTrack.gym.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {

    private Long id;

    private String username;

    private String email;

    private String gymName;

    private String profileImage;

    private String role;
}