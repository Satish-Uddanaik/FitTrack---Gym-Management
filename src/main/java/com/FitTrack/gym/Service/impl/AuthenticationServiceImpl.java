package com.FitTrack.gym.Service.impl;


import com.FitTrack.gym.config.JwtService;
import com.FitTrack.gym.dto.request.LoginRequest;
import com.FitTrack.gym.dto.request.RegisterRequest;
import com.FitTrack.gym.dto.response.AuthResponse;
import com.FitTrack.gym.Entity.User;
import com.FitTrack.gym.enums.Role;
import com.FitTrack.gym.Repo.UserRepository;
import com.FitTrack.gym.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

        System.out.println("Username = " + request.getUsername());
        System.out.println("Email = " + request.getEmail());

        System.out.println("Username exists = "
                + repository.existsByUsername(request.getUsername()));

        System.out.println("Email exists = "
                + repository.existsByEmail(request.getEmail()));

        if (repository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .gymName(request.getGymName())
                .role(Role.ADMIN)
                .build();

        repository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();

    }

//    @Override
//    public AuthResponse register(RegisterRequest request) {
//
//        if (repository.existsByUsername(request.getUsername())) {
//            throw new RuntimeException("Username already exists");
//        }
//
//        if (repository.existsByEmail(request.getEmail())) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        User user = User.builder()
//                .username(request.getUsername())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .gymName(request.getGymName())
//                .role(Role.ADMIN)
//                .build();
//
//        repository.save(user);
//
//        String token = jwtService.generateToken(user);
//
//        return AuthResponse.builder()
//                .token(token)
//                .username(user.getUsername())
//                .email(user.getEmail())
//                .role(user.getRole().name())
//                .build();
//    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
