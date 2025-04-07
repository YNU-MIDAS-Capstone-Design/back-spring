package backend.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.Date;

import backend.spring.dto.request.project.PostProjectCommentRequestDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "project_comment")
@Table(name = "project_comment")
public class ProjectCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    private String message;

    @Column(name = "created_at")
    private Date createdAt;

    private long userId;
    private int projectId;

    public ProjectCommentEntity(PostProjectCommentRequestDto dto, int projectId, long userId) {
        this.message = dto.getComment();
        this.projectId = projectId;
        this.userId = userId;
        this.createdAt = Date.from(Instant.now());
    }
}
