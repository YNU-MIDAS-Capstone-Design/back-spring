package backend.spring.dto.request.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "댓글 수정 요청 DTO")
public class EditCommentRequestDto {

    @Schema(description = "수정할 댓글 내용", example = "댓글 수정")
    @NotBlank
    private String message;
}
