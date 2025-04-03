package backend.spring.dto.object;

import lombok.Getter;

@Getter
public class ViewTeamDto {
	private Long team_id;
	private String team_name;
	private boolean owner;

	public ViewTeamDto(Long team_id, String team_name, boolean owner) {
		this.team_id = team_id;
		this.team_name = team_name;
		this.owner = owner;
	}
}
