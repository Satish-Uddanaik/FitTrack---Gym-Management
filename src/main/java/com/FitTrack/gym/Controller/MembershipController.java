package com.FitTrack.gym.Controller;


import com.FitTrack.gym.Service.MembershipService;
import com.FitTrack.gym.dto.request.MembershipRequest;
import com.FitTrack.gym.dto.response.MembershipResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    /**
     * Add Membership Plan
     */
    @PostMapping
    public ResponseEntity<MembershipResponse> addMembership(
            @Valid @RequestBody MembershipRequest request) {

        MembershipResponse response =
                membershipService.addMembership(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Get All Membership Plans
     */
    @GetMapping
    public ResponseEntity<List<MembershipResponse>> getAllMemberships() {

        return ResponseEntity.ok(
                membershipService.getAllMemberships()
        );
    }

    /**
     * Get Membership By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<MembershipResponse> getMembershipById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                membershipService.getMembershipById(id)
        );
    }

    /**
     * Update Membership Plan
     */
    @PutMapping("/{id}")
    public ResponseEntity<MembershipResponse> updateMembership(
            @PathVariable Long id,
            @Valid @RequestBody MembershipRequest request) {

        return ResponseEntity.ok(
                membershipService.updateMembership(id, request)
        );
    }

    /**
     * Delete Membership Plan
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMembership(
            @PathVariable Long id) {

        membershipService.deleteMembership(id);

        return ResponseEntity.ok(
                "Membership deleted successfully."
        );
    }

}
