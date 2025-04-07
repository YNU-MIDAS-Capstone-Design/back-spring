package backend.spring.repository;

import backend.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByUserId(Long userId);
}
