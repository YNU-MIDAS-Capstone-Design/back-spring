package backend.spring.controller;

import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.response.notification.NotificationResponseDto;
import backend.spring.dto.response.notification.UnreadCountResponseDto;
import backend.spring.security.CustomUserDetails;
import backend.spring.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Notification", description = "알림 API")
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 목록 조회", description = "현재 로그인한 사용자의 전체 알림 목록을 시간순으로 조회")
    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(notificationService.getNotifications(userDetails.getUser()));
    }

    @Operation(summary = "읽지 않은 알림 개수 조회", description = "현재 로그인한 사용자의 읽지 않은 알림 개수를 조회")
    @GetMapping("/unread-count")
    public ResponseEntity<UnreadCountResponseDto> getUnreadCount(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(notificationService.getUnreadCount(userDetails.getUser()));
    }

    @Operation(summary = "알림 읽음 처리", description = "알림 ID를 이용해 해당 알림을 읽음 처리")
    @PostMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        boolean success = notificationService.markAsRead(id, userDetails.getUser());

        if (!success) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto("FORBIDDEN", "알림을 읽을 권한이 없습니다."));
        }

        return ResponseEntity.ok().build();
    }

}
