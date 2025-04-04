package backend.spring.dto.request.myteams;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CalendarAddRequestDto {
	private String cal_date;
	private String content;
}
