package backend.spring.dto.response;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class LoginResponseDto extends ResponseDto {

    private String token;
    private String role;

    private LoginResponseDto(String code, String message, String token, String role) {
        super(code, message);
        this.token = token;
        this.role = role;
    }

    public static ResponseEntity<LoginResponseDto> success(String token, String role) {
        return ResponseEntity.ok(new LoginResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, token, role));
    }

    public static ResponseEntity<LoginResponseDto> fail() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponseDto(ResponseCode.LOGIN_FAIL, ResponseMessage.LOGIN_FAIL, null, null));
    }
}
