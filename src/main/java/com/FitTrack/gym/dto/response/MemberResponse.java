package com.FitTrack.gym.dto.response;



import com.FitTrack.gym.enums.Gender;
import com.FitTrack.gym.enums.MemberStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MemberResponse {

    private Long id;

    private String fullName;

    private String mobile;

    private String email;

    private Integer age;

    private Gender gender;

    private String address;

    private LocalDate joiningDate;

    private LocalDate lastPaymentDate;

    private LocalDate nextBillDate;

    private MemberStatus status;

    private String membershipName;

}