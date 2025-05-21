package backend.spring.dto.object;

import backend.spring.entity.Team;
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

    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @Schema(description = "자기소개", example = "안녕하세요, 백엔드 개발자입니다.")
    private String bio;

    @Schema(description = "거주 지역", example = "경상북도")
    private String location;

    @Schema(description = "SNS 링크", example = "https://github.com/exam")
    private String sns;

    @Schema(description = "MBTI", example = "INTJ")
    private String mbti;

    @Schema(description = "직업", example = "학생")
    private String job;

    @Schema(description = "기술 스택 목록", example = "[\"CSS\", \"HTML\"]")
    private List<String> techStacks;

    @Schema(
            description = "속한 팀 목록",
            example =
                    "[" +
                            "  {" +
                            "    \"teamId\": 1," +
                            "    \"teamName\": \"AI 프로젝트 팀\"," +
                            "    \"imageUrl\": \"빨강\"," +
                            "    \"owner\": false" +
                            "  }," +
                            "  {" +
                            "    \"teamId\": 2," +
                            "    \"teamName\": \"웹 개발 팀\"," +
                            "    \"imageUrl\": \"http://localhost:8080/api/view/team_image,864bfddf-c587-407f-9c0c-ebb23ff1090a..png\"," +
                            "    \"owner\": false" +
                            "  }" +
                            "]"
    )
    private List<UserTeamDto> teams;

    @Schema(
            description = "프로필 이미지 URL",
            example = "http://localhost:8080/api/view/profile_image,123e4567-e89b-12d3-a456-426614174000.png"
    )
    private String profileImageUrl;

    public UserProfileResponse(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.bio = user.getBio();
        this.location = user.getLocation().name();
        this.sns = user.getSns();
        this.mbti = user.getMbti();
        this.job = user.getJob();

        this.techStacks = user.getTechStacks()
                .stream()
                .map(stack -> stack.getName().name())
                .collect(Collectors.toList());

        this.teams = user.getTeamList().stream()
                .map(tm -> {
                    Team t = tm.getTeam();
                    return new UserTeamDto(
                            t.getTeamId(),
                            t.getTeamName(),
                            t.getTeamImage()   // URL 그대로 반환
                            //tm.isOwner()
                    );
                })
                .collect(Collectors.toList());

        String filename = user.getProfileImageFilename();
        this.profileImageUrl = (filename == null || filename.isBlank())
                ? null
                : "http://localhost:8080/api/view/profile_image," + filename;
    }
}
