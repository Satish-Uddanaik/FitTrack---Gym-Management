package com.FitTrack.gym.Controller;



import com.FitTrack.gym.Service.MemberService;
import com.FitTrack.gym.dto.request.MemberRequest;
import com.FitTrack.gym.dto.response.MemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * Add New Member
     */
    @PostMapping
    public ResponseEntity<MemberResponse> addMember(
            @Valid @RequestBody MemberRequest request) {

        MemberResponse response = memberService.addMember(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Get All Members
     */
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers() {

        return ResponseEntity.ok(memberService.getAllMembers());
    }

    /**
     * Get Member By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(
            @PathVariable Long id) {

        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    /**
     * Search Member By Name
     */
    @GetMapping("/search")
    public ResponseEntity<List<MemberResponse>> searchMember(
            @RequestParam String keyword) {

        return ResponseEntity.ok(memberService.searchMember(keyword));
    }

    /**
     * Update Member
     */
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequest request) {

        return ResponseEntity.ok(
                memberService.updateMember(id, request)
        );
    }

    /**
     * Delete Member
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(
            @PathVariable Long id) {

        memberService.deleteMember(id);

        return ResponseEntity.ok("Member deleted successfully.");
    }

    /**
     * Get Members By Status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MemberResponse>> getMembersByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                memberService.getMembersByStatus(status)
        );
    }


    /**
     * Active Members
     */
    @GetMapping("/active")
    public ResponseEntity<List<MemberResponse>> getActiveMembers() {

        return ResponseEntity.ok(
                memberService.getActiveMembers()
        );
    }

    /**
     * Expired Members
     */
    @GetMapping("/expired")
    public ResponseEntity<List<MemberResponse>> getExpiredMembers() {

        return ResponseEntity.ok(
                memberService.getExpiredMembers()
        );
    }

    /**
     * Members Expiring Within Given Days
     */
    @GetMapping("/expiring")
    public ResponseEntity<List<MemberResponse>> getExpiringMembers(
            @RequestParam(defaultValue = "7") int days) {

        return ResponseEntity.ok(
                memberService.getMembersExpiringWithinDays(days)
        );
    }

    /**
     * Total Members
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalMembers() {

        return ResponseEntity.ok(
                memberService.getTotalMembers()
        );
    }

    /**
     * Active Members Count
     */
    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveCount() {

        return ResponseEntity.ok(
                memberService.getActiveMemberCount()
        );
    }

    /**
     * Expired Members Count
     */
    @GetMapping("/count/expired")
    public ResponseEntity<Long> getExpiredCount() {

        return ResponseEntity.ok(
                memberService.getExpiredMemberCount()
        );
    }

    @GetMapping("/recent")
    public ResponseEntity<List<MemberResponse>> getRecentMembers() {

        return ResponseEntity.ok(
                memberService.getRecentMembers()
        );

    }

    @GetMapping("/upcoming-bills")
    public ResponseEntity<List<MemberResponse>> getUpcomingBills() {

        return ResponseEntity.ok(
                memberService.getUpcomingDueBills()
        );
    }


    @GetMapping("/due-bills")
    public ResponseEntity<List<MemberResponse>> getDueBills(
            @RequestParam(defaultValue = "7") int days) {

        return ResponseEntity.ok(
                memberService.getDueBills(days)
        );
    }

}