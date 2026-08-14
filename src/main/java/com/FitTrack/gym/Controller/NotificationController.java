package com.FitTrack.gym.Controller;


import com.FitTrack.gym.dto.response.NotificationResponse;
import com.FitTrack.gym.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // ================= Get All Notifications =================

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications() {

        return ResponseEntity.ok(
                notificationService.getNotifications()
        );

    }

    // ================= Get Notification Count =================

    @GetMapping("/count")
    public ResponseEntity<Long> getNotificationCount() {

        return ResponseEntity.ok(
                notificationService.getNotificationCount()
        );

    }

}