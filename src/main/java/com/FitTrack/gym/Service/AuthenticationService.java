package com.FitTrack.gym.Service;


import com.FitTrack.gym.dto.request.LoginRequest;
import com.FitTrack.gym.dto.request.RegisterRequest;
import com.FitTrack.gym.dto.response.AuthResponse;

public interface AuthenticationService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}