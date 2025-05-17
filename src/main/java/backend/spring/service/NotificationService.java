package backend.spring.service;

import backend.spring.dto.response.notification.NotificationResponseDto;
import backend.spring.dto.response.notification.UnreadCountResponseDto;
import backend.spring.entity.Notification;
import backend.spring.entity.User;
import backend.spring.entity.enums.NotificationType;
import backend.spring.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void createNotification(User receiver, String message, NotificationType type, Long targetId) {
        Notification notification = Notification.builder()
                .receiver(receiver)
                .message(message)
                .type(type)
                .targetId(targetId)
                .build();
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> getNotifications(User user) {
        return notificationRepository.findByReceiverOrderByCreatedAtDesc(user).stream()
                .map(NotificationResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean markAsRead(Long notificationId, User user) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);

        if (optionalNotification.isEmpty()) return false;

        Notification notification = optionalNotification.get();

        if (!notification.getReceiver().getUserId().equals(user.getUserId())) {
            return false;
        }

        notification.setRead(true);
        return true;
    }

    public void send(User receiver, User sender, NotificationType type, Long targetId) {
        // 작성자 본인 행동일 경우 알림 생성하지 않음
        if (receiver.getUserId().equals(sender.getUserId())) {
            return;
        }
        String message = generateMessage(sender, type);
        createNotification(receiver, message, type, targetId);
    }

    @Transactional(readOnly = true)
    public UnreadCountResponseDto getUnreadCount(User user) {
        long count = notificationRepository.countByReceiverAndIsReadFalse(user);
        return new UnreadCountResponseDto(count);
    }
    private String generateMessage(User sender, NotificationType type) {
        return switch (type) {
            case COMMENT -> sender.getNickname() + "님이 댓글을 달았습니다.";
            case LIKE -> sender.getNickname() + "님이 좋아요를 눌렀습니다.";
            case APPLY -> sender.getNickname() + "님이 지원했습니다.";
        };
    }

}
