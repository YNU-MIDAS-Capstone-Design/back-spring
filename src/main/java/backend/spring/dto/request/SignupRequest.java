package backend.spring.dto.request;

import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.Stack;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "회원가입 요청 DTO")
public class SignupRequest {

    @Schema(description = "유저 닉네임", example = "exam")
    private String nickname;

    @Schema(description = "이메일 주소", example = "user@example.com")
    private String email;

    @Schema(description = "비밀번호", example = "strongpassword123")
    private String password;

    @Schema(description = "자기소개", example = "안녕하세요, 백엔드 개발자입니다.")
    private String bio;

    @Schema(description = "거주 지역", example = "경상북도")
    private Location location;

    @Schema(description = "SNS 링크", example = "https://github.com/exam")
    private String sns;

    @Schema(description = "기술 스택 목록", example = "[\"CSS\", \"HTML\"]")
    private List<Stack> techStacks;
}
