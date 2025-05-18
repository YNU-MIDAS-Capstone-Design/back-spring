package backend.spring.repository;

import backend.spring.entity.Notification;
import backend.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverOrderByCreatedAtDesc(User user);
    long countByReceiverAndIsReadFalse(User user);
}
