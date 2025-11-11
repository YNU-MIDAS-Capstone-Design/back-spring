package backend.spring.dto.object;

import lombok.Getter;

@Getter
public class UserInfoDto {
    private Long user_id;
    private String nickname;
    private String email;
    private String bio;
    private String location;
    private String sns;
    private String mbti;
    private String job;

    public UserInfoDto(Long user_id, String nickname, String email, String bio, String location, String sns, String mbti, String job){
        this.user_id = user_id;
        this.nickname = nickname;
        this.email = email;
        this.bio = bio;
        this.location = location;
        this.sns = sns;
        this.mbti = mbti;
        this.job = job;
    }
}
