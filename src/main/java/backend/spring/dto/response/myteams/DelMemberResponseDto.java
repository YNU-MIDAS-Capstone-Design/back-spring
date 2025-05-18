package backend.spring.dto.response.myteams;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class DelMemberResponseDto extends ResponseDto {

    private DelMemberResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<DelMemberResponseDto> success(){
        DelMemberResponseDto result = new DelMemberResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> not_existed_member(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_ID, ResponseMessage.NOT_EXISTED_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    } //해당 member_id가 존재하지 않는다.

    public static ResponseEntity<ResponseDto> not_match_user(){  //사용자가 팀장이 아닐때
        ResponseDto result = new ResponseDto(ResponseCode.NOT_MATCH_USER, ResponseMessage.NOT_MATCH_USER);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }
}
