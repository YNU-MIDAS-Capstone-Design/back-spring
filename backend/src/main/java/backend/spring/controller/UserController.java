package backend.spring.controller;

import backend.spring.dto.object.UserProfileResponse;
import backend.spring.dto.request.UpdateProfileRequest;
import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.response.SignupResponseDto;
import backend.spring.security.CustomUserDetails;
import backend.spring.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "특정 유저 프로필 조회", description = "닉네임을 기반으로 유저의 공개 프로필을 조회합니다.")
    @GetMapping("/{nickname}")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @Parameter(description = "조회할 유저의 닉네임", example = "devgizmo") @PathVariable String nickname) {
        return ResponseEntity.ok(userService.getUserProfile(nickname));
    }

    @Operation(summary = "내 프로필 조회", description = "현재 로그인한 유저의 프로필 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userService.getMyProfile(userDetails.getUsername()));
    }

    @Operation(summary = "내 프로필 수정", description = "현재 로그인한 유저의 프로필 정보를 수정합니다.")
    @PutMapping("/me")
    public ResponseEntity<ResponseDto> updateMyProfile(
            @RequestBody UpdateProfileRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userService.updateMyProfile(request, userDetails.getUsername());
        return ResponseDto.successResponse();
    }

    @Operation(summary = "이메일 중복 확인", description = "입력한 이메일이 이미 존재하는지 확인합니다.")
    @GetMapping("/check-email")
    public ResponseEntity<? extends ResponseDto> checkEmailDuplicate(@RequestParam String email) {
        if (userService.isEmailDuplicate(email)) {
            return SignupResponseDto.duplicateEmail();
        }
        return ResponseDto.successResponse();
    }

    @Operation(summary = "닉네임 중복 확인", description = "입력한 닉네임이 이미 존재하는지 확인합니다.")
    @GetMapping("/check-nickname")
    public ResponseEntity<? extends ResponseDto> checkNicknameDuplicate(@RequestParam String nickname) {
        if (userService.isNicknameDuplicate(nickname)) {
            return SignupResponseDto.duplicateNickname();
        }
        return ResponseDto.successResponse();
    }


}
