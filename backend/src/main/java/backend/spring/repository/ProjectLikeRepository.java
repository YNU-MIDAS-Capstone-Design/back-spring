package backend.spring.repository;

import backend.spring.entity.Project;
import backend.spring.entity.ProjectLike;
import backend.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectLikeRepository extends JpaRepository<ProjectLike, Long> {

    Optional<ProjectLike> findByProjectAndUser(Project project, User user);

    boolean existsByProjectAndUser(Project project, User user);
}
