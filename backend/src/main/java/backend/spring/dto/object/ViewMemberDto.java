package backend.spring.dto.object;

import lombok.Getter;

@Getter
public class ViewMemberDto {
	private Long user_id;
	private String name;
	private boolean owner;
	private String stacks;

	public ViewMemberDto(Long user_id, String name, boolean owner, String stacks) {
		this.user_id = user_id;
		this.name = name;
		this.owner = owner;
		this.stacks = stacks;
	}
}
