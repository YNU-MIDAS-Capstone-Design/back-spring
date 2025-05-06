package backend.spring.dto.object;

import backend.spring.entity.ProjectComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "프로젝트 댓글 DTO")
public class ProjectCommentDto {

    private Long commentId;
    private String message;
    private String writer;
    private LocalDateTime createdAt;

    public ProjectCommentDto(ProjectComment comment) {
        this.commentId = comment.getCommentId();
        this.message = comment.getMessage();
        this.writer = comment.getUser().getNickname();
        this.createdAt = comment.getCreated_at();
    }
}
