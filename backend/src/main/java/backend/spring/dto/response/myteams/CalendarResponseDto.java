package backend.spring.dto.response.myteams;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.object.ViewCalendarDto;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class CalendarResponseDto extends ResponseDto {
	Integer year;
	Integer month;
	List<ViewCalendarDto> calendars;

	private CalendarResponseDto(Integer year, Integer month, List<ViewCalendarDto> calendars){
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.year = year;
		this.month = month;
		this.calendars = calendars;
	}

	public static ResponseEntity<CalendarResponseDto> success(Integer year, Integer month, List<ViewCalendarDto> calendars){
		CalendarResponseDto result = new CalendarResponseDto(year, month, calendars);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	//not_existed_user

	public static ResponseEntity<ResponseDto> not_existed_team(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_TEAM, ResponseMessage.NOT_EXISTED_TEAM);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	} //해당 team_id가 존재하지 않는다.

	public static ResponseEntity<ResponseDto> not_existed_content(){
		ResponseDto result = new ResponseDto(ResponseCode.EMPTY_RESULT, "일정이 존재하지 않습니다.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
