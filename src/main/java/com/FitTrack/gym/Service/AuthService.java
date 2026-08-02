package com.FitTrack.gym.Service;


import com.FitTrack.gym.Exception.OurException;
import com.FitTrack.gym.config.JwtService;
import com.FitTrack.gym.dto.request.LoginRequest;
import com.FitTrack.gym.dto.request.RegisterRequest;
import com.FitTrack.gym.dto.response.AuthResponse;
import com.FitTrack.gym.Entity.User;
import com.FitTrack.gym.enums.Role;
import com.FitTrack.gym.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        if (repository.existsByUsername(request.getUsername())) {
            throw new OurException("Username already exists");
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new OurException("Email already exists");
        }

        User user = User.builder()

                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .gymName(request.getGymName())
                .role(Role.ADMIN)
                .build();

        repository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()

                .token(jwtToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())

                .build();

    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getUsername(),
                        request.getPassword()

                )

        );

        User user = repository.findByUsername(request.getUsername())

                .orElseThrow(() ->
                        new OurException("User not found"));

        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()

                .token(jwtToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())

                .build();

    }

}