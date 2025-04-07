package backend.spring.dto.response.project;

import backend.spring.dto.response.ResponseDto;
import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;

@Getter
public class PostProjectResponseDto extends ResponseDto{

    private PostProjectResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<PostProjectResponseDto> success(){
        PostProjectResponseDto result = new PostProjectResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
