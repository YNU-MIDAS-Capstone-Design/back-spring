package backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.spring.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
