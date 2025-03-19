package backend.spring.dto.object;

import lombok.Getter;

@Getter
public class ViewProjectData {
	private Long project_id;
	private String title;
	private String description;

	private ViewProjectData(Long project_id, String title, String description) {
		this.project_id = project_id;
		this.title = title;
		this.description = description;
	}
}
