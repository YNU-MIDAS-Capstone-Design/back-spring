package backend.spring.dto.request.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PostProjectRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
