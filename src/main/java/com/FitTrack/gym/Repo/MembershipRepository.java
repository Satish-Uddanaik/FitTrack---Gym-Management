package com.FitTrack.gym.Repo;



import com.FitTrack.gym.Entity.Membership;
import com.FitTrack.gym.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    //dashboard
    long countByUser(User user);

    //membership
    List<Membership> findByUser(User user);
    Optional<Membership> findByIdAndUser(Long id, User user);

    boolean existsByPlanNameAndUser(String planName, User user);

    @Query("""
SELECT m
FROM Membership m
WHERE m.user = :user
""")
    List<Membership> findAllByUser(@Param("user") User user);


}