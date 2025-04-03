package backend.spring.dto.object;

import backend.spring.entity.enums.Position;
import lombok.Getter;

@Getter
public class ViewMemberDto {
	private Long member_id;
	private String name;
	private boolean owner;
	private Position team_role;

	public ViewMemberDto(Long member_id, String name, boolean owner, Position team_role) {
		this.member_id = member_id;
		this.name = name;
		this.owner = owner;
		this.team_role = team_role;
	}
}
