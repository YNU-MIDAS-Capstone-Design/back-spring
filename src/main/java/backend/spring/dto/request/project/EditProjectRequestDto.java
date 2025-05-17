package backend.spring.dto.request.project;

import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.Processing;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "프로젝트 수정 요청 DTO")
public class EditProjectRequestDto {

    @Schema(description = "제목", example = "수정된 제목")
    @NotBlank
    private String title;

    @Schema(description = "요약", example = "수정된 요약")
    private String description;

    @Schema(description = "내용", example = "수정된 내용")
    private String content;

    @Schema(description = "모집 상태", example = "모집완료")
    @NotNull
    private Processing processing;

    @Schema(description = "모집 분야", example = "프론트엔드")
    @NotBlank
    private String recruitmentField;

    @Schema(description = "모집 인원", example = "6")
    @NotNull
    private Integer people;

    @Schema(description = "지역", example = "경상북도")
    @NotNull
    private Location meet_location;
}
