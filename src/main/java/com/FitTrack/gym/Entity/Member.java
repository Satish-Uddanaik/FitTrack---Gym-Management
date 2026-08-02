package com.FitTrack.gym.Entity;



import com.FitTrack.gym.enums.Gender;
import com.FitTrack.gym.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(unique = true)
    private String email;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    private LocalDate joiningDate;

    private LocalDate lastPaymentDate;

    private LocalDate nextBillDate;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private String profileImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}