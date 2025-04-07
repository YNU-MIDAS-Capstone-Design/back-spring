package backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import backend.spring.entity.ProjectComment;

public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Long> {

    @Query(
            value =
                    "SELECT " +
                            "U.nickname AS nickname, " +
                            "C.created_at AS createdAt, " +
                            "C.content AS content " +
                            "FROM project_comment AS C " +
                            "INNER JOIN user AS U ON C.user_id = U.user_id " +
                            "WHERE C.project_id = ?1 " +
                            "ORDER BY C.created_at DESC",
            nativeQuery = true
    )
    List<GetProjectCommentListResultSet> getProjectCommentList(Integer projectId);
}
