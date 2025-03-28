package backend.spring.controller;

import backend.spring.dto.object.UserProfileResponse;
import backend.spring.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        return ResponseEntity.ok(userService.getMyProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<String> updateMyProfile(@RequestBody Map<String, Object> body) {
        String bio = (String) body.get("bio");
        List<String> stacks = (List<String>) body.get("techStacks");
        userService.updateMyProfile(bio, stacks);
        return ResponseEntity.ok("Profile updated");
    }
}