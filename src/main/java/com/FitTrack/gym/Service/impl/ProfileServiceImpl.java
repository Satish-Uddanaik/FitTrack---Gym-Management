package com.FitTrack.gym.Service.impl;

import com.FitTrack.gym.Entity.User;
import com.FitTrack.gym.Exception.ResourceAlreadyExistsException;
import com.FitTrack.gym.Exception.ResourceNotFoundException;
import com.FitTrack.gym.Repo.UserRepository;
import com.FitTrack.gym.config.JwtService;
import com.FitTrack.gym.dto.ApiResponse;
import com.FitTrack.gym.dto.request.ChangePasswordRequest;
import com.FitTrack.gym.dto.request.ProfileUpdateRequest;
import com.FitTrack.gym.dto.response.ProfileResponse;
import com.FitTrack.gym.Service.ProfileService;
import com.FitTrack.gym.dto.response.ProfileUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ProfileResponse getProfile() {

        User user = getLoggedInUser();

        return mapToResponse(user);
    }

    @Override
    public ProfileUpdateResponse updateProfile(ProfileUpdateRequest request) {

        User user = getLoggedInUser();

        // Username validation
        if (!user.getUsername().equals(request.getUsername())
                && userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        // Email validation
        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setGymName(request.getGymName());

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return ProfileUpdateResponse.builder()
                .profile(mapToResponse(user))
                .token(token)
                .message("Profile updated successfully")
                .build();
    }

    @Override
    public ApiResponse changePassword(ChangePasswordRequest request) {

        User user = getLoggedInUser();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return ApiResponse.builder()
                .success(true)
                .message("Password changed successfully")
                .build();
    }

    // ================= Helper Methods =================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private ProfileResponse mapToResponse(User user) {

        return ProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .gymName(user.getGymName())
                .profileImage(user.getProfileImage())
                .role(user.getRole().name())
                .build();
    }
}
