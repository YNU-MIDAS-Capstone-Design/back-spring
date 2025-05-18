package backend.spring.repository;

import backend.spring.entity.Project;
import backend.spring.entity.ProjectApplicant;
import backend.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectApplicantRepository extends JpaRepository<ProjectApplicant, Long> {

    boolean existsByProjectAndUser(Project project, User user);

    List<ProjectApplicant> findByProject(Project project);
    Optional<ProjectApplicant> findByProjectAndUser(Project project, User user);

    List<ProjectApplicant> findAllByUser(User user);

}
