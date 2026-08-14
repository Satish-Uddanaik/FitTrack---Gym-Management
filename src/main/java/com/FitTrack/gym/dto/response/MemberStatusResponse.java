package com.FitTrack.gym.dto.response;


import com.FitTrack.gym.enums.MemberStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberStatusResponse {

    private MemberStatus status;

    private Long count;

}