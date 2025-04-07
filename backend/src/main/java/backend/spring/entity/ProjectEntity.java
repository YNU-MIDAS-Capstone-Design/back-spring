package backend.spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

import backend.spring.dto.request.project.PostProjectRequestDto;
import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.Processing;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private Long userId;
    private String title;
    private String description;
    private String content;
    private String recruitmentField;

    @Enumerated(EnumType.STRING)
    private Processing processing;
    private int people;
    private Date startDate;
    private String languages; //스택으로 해야됨.
    private Date deadline;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    @Column(name = "created_at")
    private Date createdAt;

    @Enumerated(EnumType.STRING)
        private Location meetLocation;

    public ProjectEntity(PostProjectRequestDto dto, long userId) {
        this.userId = userId;
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.content = dto.getContent();
        this.recruitmentField = dto.getRecruitmentField();
        this.processing = dto.getProcessing();
        this.people = dto.getPeople();
        this.startDate = dto.getStartDate();
        this.languages = dto.getLanguages();
        this.deadline = dto.getDeadline();
        this.commentCount = 0;
        this.viewCount = 0;
        this.likeCount = 0;
        this.createdAt = Date.from(Instant.now());
        this.meetLocation = dto.getMeetLocation();

    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }
}
