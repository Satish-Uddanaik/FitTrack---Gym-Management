package com.FitTrack.gym.Repo;



import com.FitTrack.gym.Entity.Member;
import com.FitTrack.gym.Entity.Membership;
import com.FitTrack.gym.Entity.User;
import com.FitTrack.gym.dto.response.MemberResponse;
import com.FitTrack.gym.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {


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


    List<Member> findTop5ByUserOrderByCreatedAtDesc(User user);

    List<Member> findByUserAndNextBillDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate
    );

    long countByMembership(Membership membership);

    long countByUser(User user);

    long countByUserAndStatus(User user, MemberStatus status);

    long countByUserAndNextBillDate(User user, LocalDate nextBillDate);

    @Query("""
SELECT COALESCE(SUM(m.membership.price),0)
FROM Member m
WHERE m.user = :user
""")
    BigDecimal getTotalRevenue(@Param("user") User user);

    @Query("""
SELECT COALESCE(SUM(m.membership.price),0)
FROM Member m
WHERE m.user = :user
AND YEAR(m.joiningDate)=YEAR(CURRENT_DATE)
AND MONTH(m.joiningDate)=MONTH(CURRENT_DATE)
""")
    BigDecimal getMonthlyRevenue(@Param("user") User user);

}