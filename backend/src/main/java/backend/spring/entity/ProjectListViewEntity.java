package backend.spring.entity;

import jakarta.persistence.*;
        import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "project_list_view")
@Table(name = "project_list_view")
public class ProjectListViewEntity {
    @Id
    private int projectId;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private int commentCount;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    private String email;
    private String nickname;
}
