package backend.spring.dto.object;

import lombok.Getter;

@Getter
public class ViewCalendarDto {
	private Long cal_id;
	private String content;

	public ViewCalendarDto(Long cal_id, String content) {
		this.cal_id = cal_id;
		this.content = content;
	}
}
