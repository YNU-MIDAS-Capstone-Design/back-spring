package backend.spring.dto.response.myteams;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class CalendarEditResponseDto extends ResponseDto{
	private CalendarEditResponseDto(){super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

	public static ResponseEntity<CalendarEditResponseDto> success(){
		CalendarEditResponseDto result = new CalendarEditResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	//not_existed_user

	public static ResponseEntity<ResponseDto> not_existed_teams(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_TEAM, ResponseMessage.NOT_EXISTED_TEAM);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	} //해당 team_id가 존재하지 않는다. (add)

	public static ResponseEntity<ResponseDto> not_existed_cal(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_ID, "해당 일정이 존재하지 않습니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	} //해당 cal_id(일정)이 존재하지 않는다. (delete, modify)
}
