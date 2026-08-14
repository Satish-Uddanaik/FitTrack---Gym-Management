package com.FitTrack.gym.Service;

import com.FitTrack.gym.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> getNotifications();

    Long getNotificationCount();

}