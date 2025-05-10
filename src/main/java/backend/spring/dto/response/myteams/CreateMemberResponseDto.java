package backend.spring.dto.response.myteams;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class CreateMemberResponseDto extends ResponseDto {
	String nickname;

	private CreateMemberResponseDto(String nickname){
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.nickname = nickname;
	}

	public static ResponseEntity<CreateMemberResponseDto> success(String nickname){
		CreateMemberResponseDto result = new CreateMemberResponseDto(nickname);
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

	public static ResponseEntity<ResponseDto> not_existed_nickname(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, "해당 닉네임이 존재하지 않습니다.");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
	}

	//missing_required_data
}
