package com.FitTrack.gym.Repo;



import com.FitTrack.gym.Entity.Membership;
import com.FitTrack.gym.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    //dashboard
    long countByUser(User user);

    //membership
    List<Membership> findByUser(User user);
    Optional<Membership> findByIdAndUser(Long id, User user);

    boolean existsByPlanNameAndUser(String planName, User user);

}