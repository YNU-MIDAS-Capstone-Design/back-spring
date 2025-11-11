package backend.spring.dto.response;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import backend.spring.dto.object.UserInfoDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class AdminUserInfoResponseDto extends ResponseDto{
    List<UserInfoDto> users;

    public AdminUserInfoResponseDto(List<UserInfoDto> users) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.users = users;
    }

    public static ResponseEntity<AdminUserInfoResponseDto> success(List<UserInfoDto> users){
        AdminUserInfoResponseDto result = new AdminUserInfoResponseDto(users);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
