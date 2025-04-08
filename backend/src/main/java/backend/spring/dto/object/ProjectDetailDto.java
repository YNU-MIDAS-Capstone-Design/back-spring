package backend.spring.dto.object;

import backend.spring.entity.Project;
import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.Processing;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "프로젝트 상세 정보 DTO")
public class ProjectDetailDto {

    private Long projectId;
    private String title;
    private String description;
    private String content;
    private Processing processing;
    private String recruitmentField;
    private Integer people;
    private Location meet_location;
    private String writer;
    private Integer likeCount;
    private Integer viewCount;
    private LocalDateTime createdAt;

    public ProjectDetailDto(Project project) {
        this.projectId = project.getProjectId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.content = project.getContent();
        this.processing = project.getProcessing();
        this.recruitmentField = project.getRecruitmentField();
        this.people = project.getPeople();
        this.meet_location = project.getMeet_location();
        this.writer = project.getUser().getNickname();
        this.likeCount = project.getLikeCount();
        this.viewCount = project.getViewCount();
        this.createdAt = project.getCreated_at();
    }
}
