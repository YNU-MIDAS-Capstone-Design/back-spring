package backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.spring.entity.ProjectLike;

@Repository
public interface ProjectLikeRepository extends JpaRepository<ProjectLike, Long> {

    @Transactional
    void deleteByProjectId(Integer projectId);
}
