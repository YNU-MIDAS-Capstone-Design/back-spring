package backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.spring.entity.ProjectEntity;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{
    boolean existsByProjectId(int projectId);
}