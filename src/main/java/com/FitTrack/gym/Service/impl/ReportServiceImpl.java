package com.FitTrack.gym.Service.impl;

import com.FitTrack.gym.Entity.User;
import com.FitTrack.gym.Exception.ResourceNotFoundException;
import com.FitTrack.gym.Repo.MemberRepository;
import com.FitTrack.gym.Repo.MembershipRepository;
import com.FitTrack.gym.Repo.UserRepository;
import com.FitTrack.gym.dto.response.DashboardReportResponse;
import com.FitTrack.gym.enums.MemberStatus;
import com.FitTrack.gym.Service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.FitTrack.gym.Entity.Membership;
import com.FitTrack.gym.dto.response.MemberStatusResponse;
import com.FitTrack.gym.dto.response.MembershipStatsResponse;
import com.FitTrack.gym.dto.response.RevenueResponse;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    @Override
    public DashboardReportResponse getDashboardReport() {

        User user = getLoggedInUser();

        Long totalMembers = memberRepository.countByUser(user);

        Long activeMembers = memberRepository.countByUserAndStatus(
                user,
                MemberStatus.ACTIVE
        );

        Long expiredMembers = memberRepository.countByUserAndStatus(
                user,
                MemberStatus.EXPIRED
        );

        Long inactiveMembers = memberRepository.countByUserAndStatus(
                user,
                MemberStatus.INACTIVE
        );

        Long totalMembershipPlans = membershipRepository.countByUser(user);

        Long todayDueBills = memberRepository.countByUserAndNextBillDate(
                user,
                LocalDate.now()
        );

        BigDecimal totalRevenue = memberRepository.getTotalRevenue(user);

        BigDecimal monthlyRevenue = memberRepository.getMonthlyRevenue(user);

        return DashboardReportResponse.builder()
                .totalMembers(totalMembers)
                .activeMembers(activeMembers)
                .expiredMembers(expiredMembers)
                .inactiveMembers(inactiveMembers)
                .totalMembershipPlans(totalMembershipPlans)
                .todayDueBills(todayDueBills)
                .totalRevenue(totalRevenue)
                .monthlyRevenue(monthlyRevenue)
                .build();
    }

    @Override
    public List<MemberStatusResponse> getMemberStatus() {

        User user = getLoggedInUser();

        List<MemberStatusResponse> list = new ArrayList<>();

        list.add(
                MemberStatusResponse.builder()
                        .status(MemberStatus.ACTIVE)
                        .count(memberRepository.countByUserAndStatus(
                                user,
                                MemberStatus.ACTIVE))
                        .build()
        );

        list.add(
                MemberStatusResponse.builder()
                        .status(MemberStatus.EXPIRED)
                        .count(memberRepository.countByUserAndStatus(
                                user,
                                MemberStatus.EXPIRED))
                        .build()
        );

        list.add(
                MemberStatusResponse.builder()
                        .status(MemberStatus.INACTIVE)
                        .count(memberRepository.countByUserAndStatus(
                                user,
                                MemberStatus.INACTIVE))
                        .build()
        );

        return list;
    }



    @Override
    public List<MembershipStatsResponse> getMembershipStats() {

        User user = getLoggedInUser();

        List<Membership> memberships =
                membershipRepository.findAllByUser(user);

        List<MembershipStatsResponse> list = new ArrayList<>();

        for (Membership membership : memberships) {

            list.add(

                    MembershipStatsResponse.builder()
                            .membershipId(membership.getId())
                            .planName(membership.getPlanName())
                            .memberCount(
                                    memberRepository.countByMembership(
                                            membership
                                    )
                            )
                            .build()

            );

        }

        return list;
    }




    @Override
    public RevenueResponse getRevenue() {

        User user = getLoggedInUser();

        return RevenueResponse.builder()
                .totalRevenue(
                        memberRepository.getTotalRevenue(user)
                )
                .monthlyRevenue(
                        memberRepository.getMonthlyRevenue(user)
                )
                .build();

    }


    // ================= Logged In User =================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

    }

}