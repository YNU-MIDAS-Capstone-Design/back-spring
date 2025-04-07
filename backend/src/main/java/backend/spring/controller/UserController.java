package backend.spring.controller;

import backend.spring.dto.object.UserProfileResponse;
import backend.spring.dto.request.UpdateProfileRequest;
import backend.spring.dto.response.ResponseDto;
import backend.spring.security.CustomUserDetails;
import backend.spring.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.getUserProfile(nickname));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userService.getMyProfile(userDetails.getUsername()));
    }

    @PutMapping("/me")
    public ResponseEntity<ResponseDto> updateMyProfile(
            @RequestBody UpdateProfileRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userService.updateMyProfile(request, userDetails.getUsername());
        return ResponseDto.successResponse();
    }
}
