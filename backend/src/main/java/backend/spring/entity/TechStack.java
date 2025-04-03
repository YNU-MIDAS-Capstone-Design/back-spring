package backend.spring.entity;

import backend.spring.entity.enums.Stack;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Stack name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public TechStack(Stack name) {
        this.name = name;
    }

    public void setName(Stack name) {
        this.name = name;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
