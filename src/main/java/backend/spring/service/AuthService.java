package backend.spring.service;

import backend.spring.dto.request.AuthRequest;
import backend.spring.dto.request.SignupRequest;
import backend.spring.dto.response.LoginResponseDto;
import backend.spring.dto.response.SignupResponseDto;
import backend.spring.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public ResponseEntity<SignupResponseDto> signup(SignupRequest request) {
        return userService.registerUser(request);
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
