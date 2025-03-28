package backend.spring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_tech_stacks")
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String techStack;  // 실제 컬럼명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 이름을 다르게 사용할 수 있도록 헷갈리지 않게 메서드 추가
    public String getName() {
        return techStack;
    }

    public void setName(String name) {
        this.techStack = name;
    }
}
