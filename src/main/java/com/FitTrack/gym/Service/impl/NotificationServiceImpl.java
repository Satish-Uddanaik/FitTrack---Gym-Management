package com.FitTrack.gym.Service.impl;

import com.FitTrack.gym.Entity.Member;
import com.FitTrack.gym.Entity.User;
import com.FitTrack.gym.Exception.ResourceNotFoundException;
import com.FitTrack.gym.Repo.MemberRepository;
import com.FitTrack.gym.Repo.UserRepository;
import com.FitTrack.gym.dto.response.NotificationResponse;
import com.FitTrack.gym.enums.MemberStatus;
import com.FitTrack.gym.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    @Override
    public List<NotificationResponse> getNotifications() {

        User user = getLoggedInUser();

        List<Member> members = memberRepository.findByUser(user);

        List<NotificationResponse> notifications = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (Member member : members) {

            // ================= Due Today =================

            if (member.getNextBillDate() != null &&
                    member.getNextBillDate().isEqual(today)) {

                notifications.add(
                        NotificationResponse.builder()
                                .title("Payment Due")
                                .message(member.getFullName() + "'s payment is due today.")
                                .type("DUE")
                                .build()
                );
            }

            // ================= Upcoming Bills =================

            if (member.getNextBillDate() != null &&
                    member.getNextBillDate().isAfter(today) &&
                    !member.getNextBillDate().isAfter(today.plusDays(3))) {

                notifications.add(
                        NotificationResponse.builder()
                                .title("Upcoming Payment")
                                .message(member.getFullName()
                                        + "'s payment is due on "
                                        + member.getNextBillDate())
                                .type("UPCOMING")
                                .build()
                );
            }

            // ================= Expired Membership =================

            if (member.getStatus() == MemberStatus.EXPIRED) {

                notifications.add(
                        NotificationResponse.builder()
                                .title("Membership Expired")
                                .message(member.getFullName()
                                        + "'s membership has expired.")
                                .type("EXPIRED")
                                .build()
                );
            }

        }

        return notifications;
    }

    @Override
    public Long getNotificationCount() {

        return (long) getNotifications().size();

    }

    // ================= Helper Method =================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}