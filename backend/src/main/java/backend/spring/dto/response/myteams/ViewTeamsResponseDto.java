package backend.spring.dto.response.myteams;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.object.ViewTeamDto;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class ViewTeamsResponseDto extends ResponseDto {
	List<ViewTeamDto> myTeams;

	private ViewTeamsResponseDto(List<ViewTeamDto> myTeams) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.myTeams = myTeams;
	}

	public static ResponseEntity<ViewTeamsResponseDto> success(List<ViewTeamDto> myTeams){
		ViewTeamsResponseDto result = new ViewTeamsResponseDto(myTeams);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	//not_existed_user

	public static ResponseEntity<ResponseDto> zero_team(){ //들어 있는 팀이 한명도 없을 때
		ResponseDto result = new ResponseDto(ResponseCode.EMPTY_RESULT, "속한 팀이 없습니다.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
