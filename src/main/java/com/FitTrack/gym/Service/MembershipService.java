package com.FitTrack.gym.Service;


import com.FitTrack.gym.dto.request.MembershipRequest;
import com.FitTrack.gym.dto.response.MembershipResponse;

import java.util.List;

public interface MembershipService {

    MembershipResponse addMembership(MembershipRequest request);

    List<MembershipResponse> getAllMemberships();

    MembershipResponse getMembershipById(Long id);

    MembershipResponse updateMembership(Long id, MembershipRequest request);

    void deleteMembership(Long id);
}