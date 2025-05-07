package backend.spring.repository;

import backend.spring.entity.Project;
import backend.spring.entity.ProjectApplicant;
import backend.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectApplicantRepository extends JpaRepository<ProjectApplicant, Long> {

    boolean existsByProjectAndUser(Project project, User user);

    List<ProjectApplicant> findByProject(Project project);
}

