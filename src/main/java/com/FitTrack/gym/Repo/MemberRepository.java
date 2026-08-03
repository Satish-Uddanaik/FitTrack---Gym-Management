package com.FitTrack.gym.Repo;



import com.FitTrack.gym.Entity.Member;
import com.FitTrack.gym.Entity.Membership;
import com.FitTrack.gym.Entity.User;
import com.FitTrack.gym.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //dashboard
    long countByUser(User user);

    long countByUserAndStatus(User user, MemberStatus status);

    long countByUserAndNextBillDate(User user, LocalDate nextBillDate);

    //member
    List<Member> findByUser(User user);

    List<Member> findByMembership(Membership membership);

    List<Member> findByStatus(MemberStatus status);

    List<Member> findByNextBillDate(LocalDate nextBillDate);

    List<Member> findByFullNameContainingIgnoreCase(String fullName);

    Optional<Member> findByIdAndUser(Long id, User user);

    boolean existsByMobile(String mobile);

    Optional<Member> findByMobile(String mobile);

    List<Member> findByUserAndStatus(User user, MemberStatus status);

    List<Member> findByUserAndNextBillDate(LocalDate date, User user);

    List<Member> findByUserAndFullNameContainingIgnoreCase(User user, String name);

}