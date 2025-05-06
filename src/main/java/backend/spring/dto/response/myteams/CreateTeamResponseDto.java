package backend.spring.dto.response.myteams;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class CreateTeamResponseDto extends ResponseDto {

	private CreateTeamResponseDto() {super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

	public static ResponseEntity<CreateTeamResponseDto> success(){
		CreateTeamResponseDto result = new CreateTeamResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	//no_existed_user
}
