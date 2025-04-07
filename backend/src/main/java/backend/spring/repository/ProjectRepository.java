package backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.spring.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByProjectId(int projectId);
}