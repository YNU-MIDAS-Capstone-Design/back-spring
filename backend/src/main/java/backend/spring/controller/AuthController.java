package backend.spring.controller;

import backend.spring.domain.User;
import backend.spring.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ✅ 회원가입 엔드포인트
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        authService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
        return ResponseEntity.ok("User registered successfully");
    }

    // ✅ 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody User user) {
        String token = authService.authenticate(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(token);
    }
}
