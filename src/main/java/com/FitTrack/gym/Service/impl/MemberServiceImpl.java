package com.FitTrack.gym.Service.impl;

import com.FitTrack.gym.Entity.Member;
import com.FitTrack.gym.Entity.Membership;
import com.FitTrack.gym.Entity.User;
import com.FitTrack.gym.Exception.OurException;
import com.FitTrack.gym.Repo.MemberRepository;
import com.FitTrack.gym.Repo.MembershipRepository;
import com.FitTrack.gym.Repo.UserRepository;
import com.FitTrack.gym.Service.MemberService;
import com.FitTrack.gym.dto.request.MemberRequest;
import com.FitTrack.gym.dto.response.MemberResponse;
import com.FitTrack.gym.enums.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    /**
     * Logged in user
     */
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new OurException("User not found"));
    }

    /**
     * Entity -> DTO
     */
    private MemberResponse mapToResponse(Member member) {

        return MemberResponse.builder()
                .id(member.getId())
                .fullName(member.getFullName())
                .mobile(member.getMobile())
                .email(member.getEmail())
                .age(member.getAge())
                .gender(member.getGender())
                .address(member.getAddress())
                .joiningDate(member.getJoiningDate())
                .lastPaymentDate(member.getLastPaymentDate())
                .nextBillDate(member.getNextBillDate())
                .status(member.getStatus())
                .membershipName(
                        member.getMembership() != null
                                ? member.getMembership().getPlanName()
                                : null
                )
                .build();
    }


    //add member
    @Override
    public MemberResponse addMember(MemberRequest request) {

        User user = getCurrentUser();

        Membership membership = membershipRepository
                .findById(request.getMembershipId())
                .orElseThrow(() ->
                        new OurException("Membership not found"));

        if (!membership.getUser().getId().equals(user.getId())) {
            throw new OurException("Access Denied");
        }

        Member member = Member.builder()
                .fullName(request.getFullName())
                .mobile(request.getMobile())
                .email(request.getEmail())
                .age(request.getAge())
                .gender(request.getGender())
                .address(request.getAddress())
                .joiningDate(request.getJoiningDate())
                .lastPaymentDate(request.getLastPaymentDate())
                .nextBillDate(request.getNextBillDate())
                .status(request.getStatus())
                .membership(membership)
                .user(user)
                .build();

        memberRepository.save(member);

        return mapToResponse(member);
    }

    //get all members
    @Override
    public List<MemberResponse> getAllMembers() {

        User user = getCurrentUser();

        return memberRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //get member by id
    @Override
    public MemberResponse getMemberById(Long id) {

        User user = getCurrentUser();

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new OurException("Member not found"));

        if (!member.getUser().getId().equals(user.getId())) {
            throw new OurException("Access Denied");
        }

        return mapToResponse(member);
    }

    //search member
    @Override
    public List<MemberResponse> searchMember(String keyword) {

        User user = getCurrentUser();

        return memberRepository
                .findByFullNameContainingIgnoreCase(keyword)
                .stream()
                .filter(member ->
                        member.getUser().getId().equals(user.getId()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //update member
    @Override
    public MemberResponse updateMember(Long id, MemberRequest request) {

        User user = getCurrentUser();

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new OurException("Member not found"));

        if (!member.getUser().getId().equals(user.getId())) {
            throw new OurException("Access Denied");
        }

        Membership membership = membershipRepository.findById(request.getMembershipId())
                .orElseThrow(() ->
                        new OurException("Membership not found"));

        if (!membership.getUser().getId().equals(user.getId())) {
            throw new OurException("Access Denied");
        }

        member.setFullName(request.getFullName());
        member.setMobile(request.getMobile());
        member.setEmail(request.getEmail());
        member.setAge(request.getAge());
        member.setGender(request.getGender());
        member.setAddress(request.getAddress());
        member.setJoiningDate(request.getJoiningDate());
        member.setLastPaymentDate(request.getLastPaymentDate());
        member.setNextBillDate(request.getNextBillDate());
        member.setStatus(request.getStatus());
        member.setMembership(membership);

        memberRepository.save(member);

        return mapToResponse(member);
    }


    //delete member
    @Override
    public void deleteMember(Long id) {

        User user = getCurrentUser();

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new OurException("Member not found"));

        if (!member.getUser().getId().equals(user.getId())) {
            throw new OurException("Access Denied");
        }

        memberRepository.delete(member);
    }

    //get members by status
    @Override
    public List<MemberResponse> getMembersByStatus(String status) {

        User user = getCurrentUser();

        MemberStatus memberStatus;

        try {
            memberStatus = MemberStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new OurException("Invalid member status");
        }

        return memberRepository.findByStatus(memberStatus)
                .stream()
                .filter(member ->
                        member.getUser().getId().equals(user.getId()))
                .map(this::mapToResponse)
                .toList();
    }

    //get memebers with Due bills
    @Override
    public List<MemberResponse> getMembersWithDueBills() {

        User user = getCurrentUser();

        LocalDate today = LocalDate.now();

        return memberRepository.findByNextBillDate(today)
                .stream()
                .filter(member ->
                        member.getUser().getId().equals(user.getId()))
                .map(this::mapToResponse)
                .toList();
    }

    //get active members
    @Override
    public List<MemberResponse> getActiveMembers() {

        User user = getCurrentUser();

        return memberRepository.findByStatus(MemberStatus.ACTIVE)
                .stream()
                .filter(member ->
                        member.getUser().getId().equals(user.getId()))
                .map(this::mapToResponse)
                .toList();
    }

    //get expired members
    @Override
    public List<MemberResponse> getExpiredMembers() {

        User user = getCurrentUser();

        return memberRepository.findByStatus(MemberStatus.EXPIRED)
                .stream()
                .filter(member ->
                        member.getUser().getId().equals(user.getId()))
                .map(this::mapToResponse)
                .toList();
    }

    //get members expiring soon
    @Override
    public List<MemberResponse> getMembersExpiringWithinDays(int days) {

        User user = getCurrentUser();

        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(days);

        return memberRepository.findByUser(user)
                .stream()
                .filter(member ->
                        member.getNextBillDate() != null &&
                                !member.getNextBillDate().isBefore(today) &&
                                !member.getNextBillDate().isAfter(endDate))
                .map(this::mapToResponse)
                .toList();
    }

    //total members
    @Override
    public Long getTotalMembers() {

        User user = getCurrentUser();

        return (long) memberRepository.findByUser(user).size();
    }

    //acive memeber count
    @Override
    public Long getActiveMemberCount() {

        User user = getCurrentUser();

        return memberRepository.findByUser(user)
                .stream()
                .filter(member ->
                        member.getStatus() == MemberStatus.ACTIVE)
                .count();
    }


    //get expired member count
    @Override
    public Long getExpiredMemberCount() {

        User user = getCurrentUser();

        return memberRepository.findByUser(user)
                .stream()
                .filter(member ->
                        member.getStatus() == MemberStatus.EXPIRED)
                .count();
    }

    //get recent members
    @Override
    public List<MemberResponse> getRecentMembers() {

        User user = getCurrentUser();

        return memberRepository
                .findTop5ByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<MemberResponse> getUpcomingDueBills() {

        User user = getCurrentUser();

        LocalDate today = LocalDate.now();

        LocalDate nextWeek = today.plusDays(7);

        return memberRepository
                .findByUserAndNextBillDateBetween(
                        user,
                        today,
                        nextWeek
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    public List<MemberResponse> getDueBills(int days) {

        User user = getCurrentUser();

        LocalDate today = LocalDate.now();

        return memberRepository
                .findByUserAndNextBillDateBetween(
                        user,
                        today,
                        today.plusDays(days)
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}