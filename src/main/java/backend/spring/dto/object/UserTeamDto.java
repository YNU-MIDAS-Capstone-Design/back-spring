package backend.spring.dto.object;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "로그인 사용자가 속한 개별 팀 정보")
public class UserTeamDto {

    @Schema(description = "팀 PK", example = "3")
    private final Long teamId;

    @Schema(description = "팀 이름", example = "Dev-Match")
    private final String teamName;

    @Schema(
            description = "팀 대표 이미지 URL",
            example = "http://localhost:8080/api/view/team_image,a3b9c4d1.png"
    )
    private final String imageUrl;

    //@Schema(description = "팀장 여부", example = "true")
    //private final boolean owner;
}
