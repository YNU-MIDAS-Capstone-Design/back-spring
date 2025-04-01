package backend.spring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String bio;

    private String location;

    private String sns;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechStack> techStacks = new ArrayList<>();

    // 커스텀 생성자 (회원가입용)
    public User(String nickname, String email, String password, String bio, String location, String sns, List<TechStack> techStacks) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.location = location;
        this.sns = sns;
        this.techStacks = techStacks;
    }

    // 필요한 setter만 직접 정의 (불변성 & 보안 고려)
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSns(String sns) {
        this.sns = sns;
    }

    public void setTechStacks(List<TechStack> techStacks) {
        this.techStacks = techStacks;
    }
}
