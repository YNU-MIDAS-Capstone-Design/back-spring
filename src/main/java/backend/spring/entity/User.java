package backend.spring.entity;

import backend.spring.entity.enums.Location;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @Enumerated(EnumType.STRING)
    private Location location;

    private String sns;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    //리스트들
    //지원한 글 리스트

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TechStack> techStacks; //사용자 스택 리스트

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ProjectComment> commentList; //댓글 리스트

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<TeamMember> teamList; //속한 팀 리스트

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Project> projectList; //작성한 글 리스트

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ProjectLike> likeList; //좋아요한 글 리스트

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}