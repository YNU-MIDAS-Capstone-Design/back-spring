package backend.spring.dto.request.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PostProjectCommentRequestDto {

    @NotBlank
    private String comment;
}
