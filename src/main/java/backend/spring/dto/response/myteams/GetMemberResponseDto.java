package backend.spring.dto.response.myteams;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.object.ViewMemberDto;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class GetMemberResponseDto extends ResponseDto {
	List<ViewMemberDto> members;

	public GetMemberResponseDto(List<ViewMemberDto> members) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.members = members;
	}

	public static ResponseEntity<GetMemberResponseDto> success(List<ViewMemberDto> members){
		GetMemberResponseDto result = new GetMemberResponseDto(members);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	//not_existed_user

	public static ResponseEntity<ResponseDto> not_existed_team(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_TEAM, ResponseMessage.NOT_EXISTED_TEAM);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	} //해당 team_id가 존재하지 않는다.

	public static ResponseEntity<ResponseDto> zero_member(){ //팀원이 한 명도 없을 때
		ResponseDto result = new ResponseDto(ResponseCode.EMPTY_RESULT, "팀원이 존재하지 않습니다.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
