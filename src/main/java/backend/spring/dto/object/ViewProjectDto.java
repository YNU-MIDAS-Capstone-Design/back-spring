package backend.spring.dto.object;

import java.util.List;

import backend.spring.entity.enums.Stack;
import lombok.Getter;

@Getter
public class ViewProjectDto {
	private Long project_id;
	private String title;
	private String description;
	private List<Stack> stackList;

	public ViewProjectDto(Long project_id, String title, String description, List<Stack> stackList) {
		this.project_id = project_id;
		this.title = title;
		this.description = description;
		this.stackList = stackList;
	}
}
