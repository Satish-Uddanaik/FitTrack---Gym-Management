package com.FitTrack.gym.Service;

import com.FitTrack.gym.dto.ApiResponse;
import com.FitTrack.gym.dto.request.ChangePasswordRequest;
import com.FitTrack.gym.dto.request.ProfileUpdateRequest;
import com.FitTrack.gym.dto.response.ProfileResponse;
import com.FitTrack.gym.dto.response.ProfileUpdateResponse;

public interface ProfileService {

    ProfileResponse getProfile();

    ProfileUpdateResponse updateProfile(ProfileUpdateRequest request);

    ApiResponse changePassword(ChangePasswordRequest request);

}