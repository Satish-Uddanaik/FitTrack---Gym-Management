package com.FitTrack.gym.Service.impl;


import com.FitTrack.gym.Entity.Membership;
import com.FitTrack.gym.Entity.User;
import com.FitTrack.gym.Exception.OurException;
import com.FitTrack.gym.Repo.MembershipRepository;
import com.FitTrack.gym.Repo.UserRepository;
import com.FitTrack.gym.Service.MembershipService;
import com.FitTrack.gym.dto.request.MembershipRequest;
import com.FitTrack.gym.dto.response.MembershipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    /**
     * Returns the currently logged-in user.
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
     * Convert Entity -> Response DTO
     */
    private MembershipResponse mapToResponse(Membership membership) {

        return MembershipResponse.builder()
                .id(membership.getId())
                .planName(membership.getPlanName())
                .durationInMonths(membership.getDurationInMonths())
                .price(membership.getPrice())
                .description(membership.getDescription())
                .build();
    }

    @Override
    public MembershipResponse addMembership(MembershipRequest request) {

        User user = getCurrentUser();

        Membership membership = Membership.builder()
                .planName(request.getPlanName())
                .durationInMonths(request.getDurationInMonths())
                .price(request.getPrice())
                .description(request.getDescription())
                .user(user)
                .build();

        membershipRepository.save(membership);

        return mapToResponse(membership);
    }

    @Override
    public List<MembershipResponse> getAllMemberships() {

        User user = getCurrentUser();

        return membershipRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MembershipResponse getMembershipById(Long id) {

        User user = getCurrentUser();

        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() ->
                        new OurException("Membership not found"));

        if (!membership.getUser().getId().equals(user.getId())) {
            throw new OurException("Access Denied");
        }

        return mapToResponse(membership);
    }

    @Override
    public MembershipResponse updateMembership(Long id,
                                               MembershipRequest request) {

        User user = getCurrentUser();

        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() ->
                        new OurException("Membership not found"));

        if (!membership.getUser().getId().equals(user.getId())) {
            throw new OurException("Access Denied");
        }

        membership.setPlanName(request.getPlanName());
        membership.setDurationInMonths(request.getDurationInMonths());
        membership.setPrice(request.getPrice());
        membership.setDescription(request.getDescription());

        membershipRepository.save(membership);

        return mapToResponse(membership);
    }

    @Override
    public void deleteMembership(Long id) {

        User user = getCurrentUser();

        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() ->
                        new OurException("Membership not found"));

        if (!membership.getUser().getId().equals(user.getId())) {
            throw new OurException("Access Denied");
        }

        membershipRepository.delete(membership);
    }
}