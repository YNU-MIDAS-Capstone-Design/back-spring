package backend.spring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "로그인 요청 DTO")
public class AuthRequest {

    @Schema(description = "유저 닉네임", example = "exam")
    private String nickname;

    @Schema(description = "비밀번호", example = "strongpassword123")
    private String password;
}
