package backend.spring.dto.object;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ViewCalendarDto {
	private Long cal_id;
	private String content;
	private String start;
	private String end;

	public ViewCalendarDto(Long cal_id, String content, String start, String end) {
		this.cal_id = cal_id;
		this.content = content;
		this.start = start;
		this.end = end;
	}
}
