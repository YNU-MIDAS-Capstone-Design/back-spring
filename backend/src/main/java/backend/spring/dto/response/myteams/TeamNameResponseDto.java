package backend.spring.dto.response.myteams;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.response.ResponseDto;

public class TeamNameResponseDto extends ResponseDto {

	private TeamNameResponseDto(){super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

	public static ResponseEntity<TeamNameResponseDto> success(){
		TeamNameResponseDto result = new TeamNameResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<ResponseDto> not_existed_user(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	public static ResponseEntity<ResponseDto> not_existed_teams(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_TEAM, ResponseMessage.NOT_EXISTED_TEAM);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
}
