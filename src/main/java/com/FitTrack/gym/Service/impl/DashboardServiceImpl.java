package com.FitTrack.gym.Service.impl;

import com.FitTrack.gym.Entity.User;
import com.FitTrack.gym.Exception.OurException;
import com.FitTrack.gym.Repo.MemberRepository;
import com.FitTrack.gym.Repo.MembershipRepository;
import com.FitTrack.gym.Repo.UserRepository;
import com.FitTrack.gym.Service.DashboardService;
import com.FitTrack.gym.dto.response.DashboardResponse;
import com.FitTrack.gym.enums.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new OurException("User not found"));
    }

    @Override
    public DashboardResponse getDashboard() {

        User user = getCurrentUser();

        return DashboardResponse.builder()

                .totalMembers(
                        memberRepository.countByUser(user)
                )

                .activeMembers(
                        memberRepository.countByUserAndStatus(
                                user,
                                MemberStatus.ACTIVE)
                )

                .inactiveMembers(
                        memberRepository.countByUserAndStatus(
                                user,
                                MemberStatus.INACTIVE)
                )

                .expiredMembers(
                        memberRepository.countByUserAndStatus(
                                user,
                                MemberStatus.EXPIRED)
                )

                .totalMembershipPlans(
                        membershipRepository.countByUser(user)
                )

                .dueBillsToday(
                        memberRepository.countByUserAndNextBillDate(
                                user,
                                LocalDate.now())
                )

                .build();
    }
}