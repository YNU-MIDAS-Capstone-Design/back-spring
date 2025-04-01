package backend.spring.dto.object;

import lombok.Getter;

@Getter
public class ViewTeamDto {
	private Long team_id;
	private String team_name;
	private boolean owner;
	private String stacks;

	public ViewTeamDto(Long team_id, String team_name, boolean owner, String stacks) {
		this.team_id = team_id;
		this.team_name = team_name;
		this.owner = owner;
		this.stacks = stacks;
	}
}
