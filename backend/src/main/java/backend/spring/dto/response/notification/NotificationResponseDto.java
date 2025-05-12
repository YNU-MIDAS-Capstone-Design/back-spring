package backend.spring.dto.response.notification;


import backend.spring.entity.Notification;
import backend.spring.entity.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponseDto {
    private Long id;
    private String message;
    private boolean isRead;
    private Long targetId;
    private NotificationType type;
    private LocalDateTime createdAt;

    public static NotificationResponseDto from(Notification notification) {
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .isRead(notification.isRead())
                .targetId(notification.getTargetId())
                .type(notification.getType())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
