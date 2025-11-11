package backend.spring.controller;

import backend.spring.dto.response.AdminDelPostResponseDto;
import backend.spring.dto.response.AdminPostSummaryResponseDto;
import backend.spring.dto.response.AdminUserInfoResponseDto;
import backend.spring.security.CustomUserDetails;
import backend.spring.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    // 사용자 전체 불러오기
    @GetMapping("/users")
    public ResponseEntity<? super AdminUserInfoResponseDto> getUsers(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return adminService.getUsers( userDetails.getUsername() );
    }

    // 게시글 전체 불러오기
    @GetMapping("/posts")
    public ResponseEntity<? super AdminPostSummaryResponseDto> getPosts(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return adminService.getAllPosts(userDetails.getUsername());
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<? super AdminDelPostResponseDto> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return adminService.deletePostById(userDetails.getUsername(), id); // 204 No Content
    }

}