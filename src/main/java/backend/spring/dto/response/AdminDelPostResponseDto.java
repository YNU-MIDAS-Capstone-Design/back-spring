package backend.spring.dto.response;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class AdminDelPostResponseDto extends ResponseDto{
    private AdminDelPostResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<AdminDelPostResponseDto> success(){
        AdminDelPostResponseDto result = new AdminDelPostResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> not_existed_post(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_ID, ResponseMessage.NOT_EXISTED_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    } //해당 project_id가 존재하지 않는다.
}
