package com.FitTrack.gym.Repo;



import com.FitTrack.gym.Entity.Membership;
import com.FitTrack.gym.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    List<Membership> findByUser(User user);

}