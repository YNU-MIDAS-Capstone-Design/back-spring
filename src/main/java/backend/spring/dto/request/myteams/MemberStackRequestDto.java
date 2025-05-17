package backend.spring.dto.request.myteams;

import backend.spring.entity.enums.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberStackRequestDto {
	private Position team_role;
}
