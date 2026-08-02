package com.FitTrack.gym.dto.request;



import com.FitTrack.gym.enums.Gender;
import com.FitTrack.gym.enums.MemberStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Mobile number is required")
    private String mobile;

    @Email(message = "Invalid email")
    private String email;

    private Integer age;

    @NotNull(message = "Gender is required")
    private Gender gender;

    private String address;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;

    @NotNull(message = "Last payment date is required")
    private LocalDate lastPaymentDate;

    @NotNull(message = "Next bill date is required")
    private LocalDate nextBillDate;

    @NotNull(message = "Status is required")
    private MemberStatus status;

    @NotNull(message = "Membership is required")
    private Long membershipId;

}
