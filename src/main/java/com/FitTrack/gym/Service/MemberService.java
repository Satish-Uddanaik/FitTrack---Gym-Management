package com.FitTrack.gym.Service;


import com.FitTrack.gym.dto.request.MemberRequest;
import com.FitTrack.gym.dto.response.MemberResponse;

import java.util.List;

public interface MemberService {

    MemberResponse addMember(MemberRequest request);

    List<MemberResponse> getAllMembers();

    MemberResponse getMemberById(Long id);

    List<MemberResponse> searchMember(String keyword);

    MemberResponse updateMember(Long id, MemberRequest request);

    void deleteMember(Long id);

    List<MemberResponse> getMembersByStatus(String status);

    List<MemberResponse> getMembersWithDueBills();

    List<MemberResponse> getExpiredMembers();

    List<MemberResponse> getActiveMembers();

    List<MemberResponse> getMembersExpiringWithinDays(int days);

    Long getTotalMembers();

    Long getActiveMemberCount();

    Long getExpiredMemberCount();

    List<MemberResponse> getRecentMembers();

    List<MemberResponse> getUpcomingDueBills();

    List<MemberResponse> getDueBills(int days);

}