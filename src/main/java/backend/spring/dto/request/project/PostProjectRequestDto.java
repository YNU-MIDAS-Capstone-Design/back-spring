package backend.spring.dto.request.project;

import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.Processing;
import backend.spring.entity.enums.Stack;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "프로젝트 등록 요청 DTO")
@Getter
@Setter
public class PostProjectRequestDto {

    @Schema(description = "제목", example = "팀원 모집")
    @NotBlank(message = "제목 작성")
    private String title;

    //@Schema(description = "요약", example = "백/프 개발자 모집")
    //private String description;

    @Schema(description = "내용", example = "팀원을 모집중입니다")
    private String content;

    @Schema(description = "모집 상태", example = "모집중")
    @NotNull(message = "필수")
    private Processing processing;

    @Schema(description = "모집 분야", example = "백엔드")
    @NotBlank(message = "필수")
    private String recruitmentField;

    @Schema(description = "모집 인원 수", example = "6")
    @NotNull(message = "필수")
    private Integer people;

    @Schema(description = "장소", example = "경기도")
    @NotNull(message = "필수")
    private Location meet_location;

    @Schema(description = "기술 스택 목록", example = "[\"Java\", \"Spring\", \"React\"]")
    @NotNull(message = "필수")
    private List<Stack> stackList;
}

