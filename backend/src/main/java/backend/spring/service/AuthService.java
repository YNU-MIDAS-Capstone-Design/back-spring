package backend.spring.service;

import backend.spring.domain.User;
import backend.spring.jwt.JwtTokenProvider;
import backend.spring.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider; // ✅ JWT 토큰 생성 클래스

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider; // ✅ 주입
    }

    // ✅ 회원가입
    public void registerUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // ✅ 비밀번호 암호화
        user.setEmail(email);

        userRepository.save(user);
    }

    // ✅ 로그인 & JWT 토큰 생성
    public String authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtTokenProvider.createToken(user.getUsername()); // ✅ JWT 토큰 생성
    }
}
