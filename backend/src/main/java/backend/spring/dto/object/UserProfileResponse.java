package backend.spring.dto.object;

import backend.spring.entity.TeamMember;
import backend.spring.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Schema(description = "유저 프로필 응답 DTO")
public class UserProfileResponse {

    @Schema(description = "유저 닉네임", example = "exam")
    private String nickname;

    @Schema(description = "자기소개", example = "안녕하세요, 백엔드 개발자입니다.")
    private String bio;

    @Schema(description = "거주 지역", example = "경상북도")
    private String location;

    @Schema(description = "SNS 링크", example = "https://github.com/exam")
    private String sns;

    @Schema(description = "기술 스택 목록", example = "[\"CSS\", \"HTML\"]")
    private List<String> techStacks;

    private List<String> teams;

    public UserProfileResponse(User user) {
        this.nickname = user.getNickname();
        this.bio = user.getBio();
        this.location = user.getLocation().name();
        this.sns = user.getSns();
        this.techStacks = user.getTechStacks()
                .stream()
                .map(stack -> stack.getName().name())
                .collect(Collectors.toList());
        this.teams = user.getTeamList().stream()
                .map(TeamMember::getTeam)
                .map(team -> team.getTeamName())
                .collect(Collectors.toList());
    }
}
