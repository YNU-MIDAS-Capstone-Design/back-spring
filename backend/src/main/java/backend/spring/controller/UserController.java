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
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User API", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    @Operation(summary = "특정 유저 프로필 조회", description = "닉네임을 기반으로 유저의 공개 프로필을 조회합니다.")
    @GetMapping("/{nickname}")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @Parameter(description = "조회할 유저의 닉네임", example = "devgizmo")
            @PathVariable String nickname
    ) {
        return ResponseEntity.ok(userService.getUserProfile(nickname));
    }

    @Operation(summary = "내 프로필 조회",
            description = "현재 로그인한 유저의 프로필 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(userService.getMyProfile(userDetails.getUsername()));
    }

    @Operation(summary = "내 프로필 수정",
            description = "bio·location·techStacks 등 변경하고 싶은 항목만 전송해 수정합니다.")
    @PatchMapping("/me")   // ← PUT → PATCH 로 변경
    public ResponseEntity<ResponseDto> updateMyProfile(

            @RequestBody UpdateProfileRequest request,
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userService.updateMyProfile(request, userDetails.getUsername());
        return ResponseDto.successResponse();
    }


    @Operation(summary = "이메일 중복 확인",
            description = "입력한 이메일이 이미 존재하는지 확인합니다.")
    @GetMapping("/check-email")
    public ResponseEntity<? extends ResponseDto> checkEmailDuplicate(@RequestParam String email) {
        return userService.isEmailDuplicate(email)
                ? SignupResponseDto.duplicateEmail()
                : ResponseDto.successResponse();
    }

    @Operation(summary = "닉네임 중복 확인",
            description = "입력한 닉네임이 이미 존재하는지 확인합니다.")
    @GetMapping("/check-nickname")
    public ResponseEntity<? extends ResponseDto> checkNicknameDuplicate(
            @RequestParam String nickname
    ) {
        if (userService.isNicknameDuplicate(nickname)) {
            return SignupResponseDto.duplicateNickname();
        }
        return ResponseDto.successResponse();
    }

    @Operation(summary = "내 프로필 이미지 업로드", description = "프로필 이미지를 multipart/form-data로 업로드하거나 교체합니다.")
    @PutMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto> updateProfileImage(
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        userService.updateMyProfileImage(imageFile, userDetails.getUsername());
        return ResponseDto.successResponse();
    }


    @Operation(summary = "내 프로필 이미지 삭제", description = "저장된 프로필 이미지를 삭제합니다.")
    @DeleteMapping("/me/image")
    public ResponseEntity<ResponseDto> deleteProfileImage(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userService.deleteMyProfileImage(userDetails.getUsername());
        return ResponseDto.successResponse();
    }

}
