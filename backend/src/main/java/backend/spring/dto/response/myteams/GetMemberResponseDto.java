package backend.spring.dto.response.myteams;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.object.ViewMemberDto;
import backend.spring.dto.response.ResponseDto;

public class GetMemberResponseDto extends ResponseDto {
	private List<ViewMemberDto> members;

	public GetMemberResponseDto(List<ViewMemberDto> members) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.members = members;
	}

	public static ResponseEntity<GetMemberResponseDto> success(List<ViewMemberDto> members){
		GetMemberResponseDto result = new GetMemberResponseDto(members);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<ResponseDto> not_existed_teams(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_TEAM, ResponseMessage.NOT_EXISTED_TEAM);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
}
