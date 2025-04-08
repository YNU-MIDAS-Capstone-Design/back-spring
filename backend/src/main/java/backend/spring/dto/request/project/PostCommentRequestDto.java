package backend.spring.dto.request.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "댓글 작성 요청 DTO")
public class PostCommentRequestDto {

    @Schema(description = "댓글 내용", example = "참여하고 싶어요")
    @NotBlank(message = "댓글 내용은 비울 수 없습니다.")
    private String message;
}
