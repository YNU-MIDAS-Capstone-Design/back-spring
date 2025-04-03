package backend.spring.dto.response;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignupResponseDto extends ResponseDto {

    public SignupResponseDto(String code, String message) {
        super(code, message);
    }

    public static ResponseEntity<SignupResponseDto> duplicateNickname() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new SignupResponseDto(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME));
    }

    public static ResponseEntity<SignupResponseDto> duplicateEmail() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new SignupResponseDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL));
    }

    public static ResponseEntity<SignupResponseDto> signupSuccess() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SignupResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS));
    }

}
