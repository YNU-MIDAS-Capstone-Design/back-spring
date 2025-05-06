package backend.spring.dto.object;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ViewCalendarDto {
	private Long cal_id;
	private String date;
	private String content;

	public ViewCalendarDto(Long cal_id, String date, String content) {
		this.cal_id = cal_id;
		this.date = date;
		this.content = content;
	}
}
