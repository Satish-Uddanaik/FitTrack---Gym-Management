package com.FitTrack.gym.Repo;



import com.FitTrack.gym.Entity.Member;
import com.FitTrack.gym.Entity.Membership;
import com.FitTrack.gym.Entity.User;
import com.FitTrack.gym.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUser(User user);

    List<Member> findByMembership(Membership membership);

    List<Member> findByStatus(MemberStatus status);

    List<Member> findByNextBillDate(LocalDate nextBillDate);

    List<Member> findByFullNameContainingIgnoreCase(String fullName);

}