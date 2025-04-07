package backend.spring.repository;

import backend.spring.entity.ProjectLikeEntity;
import backend.spring.entity.pk.LikePk;
import backend.spring.repository.resultSet.GetProjectLikeListResultSet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectLikeRepository extends JpaRepository<ProjectLikeEntity, LikePk> {
    ProjectLikeEntity findByProjectIdAndUserId(Integer projectId, long userId);
    @Query(
            value =
                    "SELECT " +
                            "U.user_id AS userId, " +
                            "U.nickname AS nickname " +
                            "FROM project_like AS L " +
                            "INNER JOIN user AS U ON L.user_id = U.user_id " +
                            "WHERE L.project_id = ?1",
            nativeQuery = true
    )
    List<GetProjectLikeListResultSet> getProjectLikeList(Integer projectId);

    @Transactional
    void deleteByProjectId(Integer projectId);
}
