package backend.spring.dto.object;

import lombok.Getter;

@Getter
public class ViewTeamDto {
	private Long team_id;
	private String team_name;
	private String team_image;
	private boolean owner;

	public ViewTeamDto(Long team_id, String team_name, String team_image, boolean owner) {
		this.team_id = team_id;
		this.team_name = team_name;
		this.team_image = team_image;
		this.owner = owner;
	}
}
