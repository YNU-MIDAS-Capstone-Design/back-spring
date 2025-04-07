package backend.spring.dto.response.project;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;


@Getter
public class PutProjectLikeResponseDto extends ResponseDto{

    private PutProjectLikeResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<PutProjectLikeResponseDto> success(){
        PutProjectLikeResponseDto result = new PutProjectLikeResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> noExistProject(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_PROJECT, ResponseMessage.NOT_EXISTED_PROJECT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    public static ResponseEntity<ResponseDto> noExistUser(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); //수정했음ㅇㅇ
    }

}