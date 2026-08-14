package com.FitTrack.gym.Controller;


import com.FitTrack.gym.dto.response.DashboardReportResponse;
import com.FitTrack.gym.dto.response.MemberStatusResponse;
import com.FitTrack.gym.dto.response.MembershipStatsResponse;
import com.FitTrack.gym.dto.response.RevenueResponse;
import com.FitTrack.gym.Service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // ================= Dashboard Summary =================

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardReportResponse> getDashboardReport() {

        return ResponseEntity.ok(
                reportService.getDashboardReport()
        );

    }

    // ================= Member Status =================

    @GetMapping("/member-status")
    public ResponseEntity<List<MemberStatusResponse>> getMemberStatus() {

        return ResponseEntity.ok(
                reportService.getMemberStatus()
        );

    }

    // ================= Membership Statistics =================

    @GetMapping("/membership-stats")
    public ResponseEntity<List<MembershipStatsResponse>> getMembershipStats() {

        return ResponseEntity.ok(
                reportService.getMembershipStats()
        );

    }

    // ================= Revenue =================

    @GetMapping("/revenue")
    public ResponseEntity<RevenueResponse> getRevenue() {

        return ResponseEntity.ok(
                reportService.getRevenue()
        );

    }

}