package backend.spring.controller;

import backend.spring.dto.request.AuthRequest;
import backend.spring.dto.request.SignupRequest;
import backend.spring.dto.response.LoginResponseDto;
import backend.spring.dto.response.SignupResponseDto;
import backend.spring.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody @Valid SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthRequest request) {
        return authService.login(request);
    }
}
