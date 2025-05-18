package backend.spring.dto.request.myteams;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTeamRequestDto {
    private String teamName;
    private String teamColor;
}
