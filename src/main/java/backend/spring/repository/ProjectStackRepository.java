package backend.spring.repository;

import backend.spring.entity.ProjectStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStackRepository extends JpaRepository<ProjectStack, Long> {
}
