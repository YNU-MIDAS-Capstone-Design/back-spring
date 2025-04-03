package backend.spring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
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


    public User(String nickname, String email, String password, String bio, String location, String sns, List<TechStack> techStacks) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.location = location;
        this.sns = sns;
        this.techStacks = techStacks;
    }


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
