package backend.spring.dto.response.myteams;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class MemberStackResponseDto extends ResponseDto {

	private MemberStackResponseDto(){super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

	public static ResponseEntity<MemberStackResponseDto> success(){
		MemberStackResponseDto result = new MemberStackResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	//not_existed_user

	public static ResponseEntity<ResponseDto> not_existed_member(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_ID, "해당 member가 존재하지 않습니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	} //해당 멤버id가 존재하지 않는다

	public static ResponseEntity<ResponseDto> not_match_user(){ //바꾸고자 하는 팀원과 사용자가 일치하지 않음
		ResponseDto result = new ResponseDto(ResponseCode.NOT_MATCH_USER, ResponseMessage.NOT_MATCH_USER);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
	}

	//missing_required_data
}
