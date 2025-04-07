package backend.spring.dto.response.project;

import backend.spring.dto.response.ResponseDto;
import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetProjectLikeResponseDto extends ResponseDto {

    public GetProjectLikeResponseDto() {super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);}

    public static ResponseEntity<GetProjectLikeResponseDto> success() {
        GetProjectLikeResponseDto result = new GetProjectLikeResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistProject() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_PROJECT, ResponseMessage.NOT_EXISTED_PROJECT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
