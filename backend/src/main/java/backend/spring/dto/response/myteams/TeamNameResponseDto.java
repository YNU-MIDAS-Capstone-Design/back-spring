package backend.spring.dto.response.myteams;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class TeamNameResponseDto extends ResponseDto {

	private TeamNameResponseDto(){super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

	public static ResponseEntity<TeamNameResponseDto> success(){
		TeamNameResponseDto result = new TeamNameResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	//not_existed_user

	public static ResponseEntity<ResponseDto> not_existed_team(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_TEAM, ResponseMessage.NOT_EXISTED_TEAM);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	} //해당 team_id가 존재하지 않는다.

	public static ResponseEntity<ResponseDto> not_match_user(){  //사용자가 팀장이 아닐때
	ResponseDto result = new ResponseDto(ResponseCode.NOT_MATCH_USER, ResponseMessage.NOT_MATCH_USER);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
	}
}
