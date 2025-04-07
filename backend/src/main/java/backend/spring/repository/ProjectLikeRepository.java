package backend.spring.repository;

import backend.spring.entity.ProjectLikeEntity;
import backend.spring.entity.pk.LikePk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectLikeRepository extends JpaRepository<ProjectLike, Long> {

    @Transactional
    void deleteByProjectId(Integer projectId);
}
