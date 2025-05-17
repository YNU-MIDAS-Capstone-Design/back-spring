package backend.spring.controller;

import backend.spring.dto.request.AuthRequest;
import backend.spring.dto.request.SignupRequest;
import backend.spring.dto.response.LoginResponseDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.response.SignupResponseDto;
import backend.spring.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "회원가입 및 로그인 관련 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "사용자로부터 회원가입 정보를 입력받아 새로운 계정을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SignupResponseDto.class),
                            examples = @ExampleObject(
                                    name = "회원가입 성공 예시",
                                    summary = "회원가입 성공",
                                    value = "{ \"code\": \"SU\", \"message\": \"Success.\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "요청 유효성 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "유효성 실패 예시",
                                    summary = "이메일 형식 오류 등",
                                    value = "{ \"code\": \"VF\", \"message\": \"Validation Failed\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "닉네임 또는 이메일 중복",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SignupResponseDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "닉네임 중복 예시",
                                            summary = "이미 사용 중인 닉네임",
                                            value = "{ \"code\": \"DN\", \"message\": \"Duplicate nickname\" }"
                                    ),
                                    @ExampleObject(
                                            name = "이메일 중복 예시",
                                            summary = "이미 사용 중인 이메일",
                                            value = "{ \"code\": \"DE\", \"message\": \"Duplicate email\" }"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "서버 오류 예시",
                                    summary = "예기치 못한 서버 오류",
                                    value = "{ \"code\": \"DBE\", \"message\": \"Datatbase error.\" }"
                            )
                    )
            )
    })
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody @Valid SignupRequest request) {
        return authService.signup(request);
    }

    @Operation(summary = "로그인", description = "닉네임과 비밀번호를 입력받아 JWT 토큰을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class),
                            examples = @ExampleObject(
                                    name = "로그인 성공 예시",
                                    value = "{ \"code\": \"SU\", \"message\": \"Success.\", \"token\": \"<JWT_TOKEN>\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "요청 유효성 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "유효성 실패 예시",
                                    value = "{ \"code\": \"VF\", \"message\": \"Validation Failed\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "로그인 실패 (정보 불일치)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class),
                            examples = @ExampleObject(
                                    name = "로그인 실패 예시",
                                    value = "{ \"code\": \"AF\", \"message\": \"Login failed\", \"token\": null }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "서버 오류 예시",
                                    value = "{ \"code\": \"DBE\", \"message\": \"Datatbase error.\" }"
                            )
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthRequest request) {
        return authService.login(request);
    }
}
