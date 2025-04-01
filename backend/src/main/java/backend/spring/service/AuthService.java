package backend.spring.service;

import backend.spring.domain.TechStack;
import backend.spring.domain.User;
import backend.spring.dto.request.AuthRequest;
import backend.spring.dto.request.SignupRequest;
import backend.spring.dto.response.LoginResponseDto;
import backend.spring.dto.response.SignupResponseDto;
import backend.spring.jwt.JwtTokenProvider;
import backend.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<SignupResponseDto> signup(SignupRequest request) {
        if (userRepository.existsByNickname(request.getNickname())) {
            return SignupResponseDto.duplicateNickname();
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return SignupResponseDto.duplicateEmail();
        }

        List<TechStack> techStacks = request.getTechStacks().stream()
                .map(TechStack::new)
                .toList();

        User user = new User(
                request.getNickname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getBio(),
                request.getLocation(),
                request.getSns(),
                techStacks
        );

        // 연관관계 설정
        for (TechStack techStack : techStacks) {
            techStack.setUser(user);
        }

        userRepository.save(user);

        return SignupResponseDto.success();
    }

    public ResponseEntity<LoginResponseDto> login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getNickname(), request.getPassword())
            );
        } catch (Exception e) {
            return LoginResponseDto.fail();
        }

        String token = jwtTokenProvider.createToken(request.getNickname());
        return LoginResponseDto.success(token);
    }
}
