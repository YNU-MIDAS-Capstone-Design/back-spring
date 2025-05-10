package backend.spring.dto.response.myteams;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class TeamImgResponseDto extends ResponseDto {
    String teamImage;

    private TeamImgResponseDto(String teamImage){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.teamImage = teamImage;
    }

    public static ResponseEntity<TeamImgResponseDto> success(String teamImage){
        TeamImgResponseDto result = new TeamImgResponseDto(teamImage);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> not_existed_team(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_TEAM, ResponseMessage.NOT_EXISTED_TEAM);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    } //해당 team_id가 존재하지 않는다.
}
