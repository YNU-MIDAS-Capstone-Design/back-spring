package backend.spring.dto.response.myteams;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.object.ViewTeamDto;
import backend.spring.dto.response.ResponseDto;

public class ViewTeamsResponseDto extends ResponseDto {
	List<ViewTeamDto> myTeams;
	Integer totalCount; //전체 팀 수
	Integer page;  //현재 페이지

	private ViewTeamsResponseDto(List<ViewTeamDto> myTeams, Integer totalCount, Integer page) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.myTeams = myTeams;
		this.totalCount = totalCount;
		this.page = page;
	}

	public static ResponseEntity<ViewTeamsResponseDto> success(List<ViewTeamDto> myTeams, Integer totalCount, Integer page){
		ViewTeamsResponseDto result = new ViewTeamsResponseDto(myTeams, totalCount, page);
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
