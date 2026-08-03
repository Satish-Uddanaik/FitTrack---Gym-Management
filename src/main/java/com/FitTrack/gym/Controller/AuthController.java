package com.FitTrack.gym.Controller;



import com.FitTrack.gym.dto.request.LoginRequest;
import com.FitTrack.gym.dto.request.RegisterRequest;
import com.FitTrack.gym.dto.response.AuthResponse;
import com.FitTrack.gym.Service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        System.out.println("REGISTER API HIT");
        AuthResponse authResponse = authenticationService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse authResponse = authenticationService.login(request);

        return ResponseEntity.ok(authResponse);
    }
}