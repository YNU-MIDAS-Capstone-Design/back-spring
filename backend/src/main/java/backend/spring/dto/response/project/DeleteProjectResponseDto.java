package backend.spring.dto.response.project;
import backend.spring.dto.response.ResponseDto;
import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;

@Getter
public class DeleteProjectResponseDto extends ResponseDto {

    private DeleteProjectResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<DeleteProjectResponseDto> success() {
        DeleteProjectResponseDto result = new DeleteProjectResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> noExistBoard() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_PROJECT, ResponseMessage.NOT_EXISTED_PROJECT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    public static ResponseEntity<ResponseDto> noPermission() {
        ResponseDto result = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }
}
