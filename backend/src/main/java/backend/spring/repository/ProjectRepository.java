package backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.spring.entity.ProjectEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer>{
    boolean existsByProjectId(int projectId);
    //쿼리 추가해야함


}