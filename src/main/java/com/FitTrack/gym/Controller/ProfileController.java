package com.FitTrack.gym.Controller;


import com.FitTrack.gym.dto.ApiResponse;
import com.FitTrack.gym.dto.request.ChangePasswordRequest;
import com.FitTrack.gym.dto.request.ProfileUpdateRequest;
import com.FitTrack.gym.dto.response.ProfileResponse;
import com.FitTrack.gym.dto.response.ProfileUpdateResponse;
import com.FitTrack.gym.Service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // ================= Get Logged-in User Profile =================

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {

        return ResponseEntity.ok(profileService.getProfile());

    }

    // ================= Update Profile =================

    @PutMapping
    public ResponseEntity<ProfileUpdateResponse> updateProfile(
            @Valid @RequestBody ProfileUpdateRequest request) {

        return ResponseEntity.ok(
                profileService.updateProfile(request)
        );

    }

    // ================= Change Password =================

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        return ResponseEntity.ok(
                profileService.changePassword(request)
        );

    }

}
