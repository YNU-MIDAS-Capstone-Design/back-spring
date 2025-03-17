package backend.spring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") // ✅ DB 테이블 이름 명확히 지정
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // ✅ 중복 방지
    private String username;

    @Column(nullable = false) // ✅ NOT NULL 적용
    private String password;

    @Column(unique = true)
    private String email;

    @Column(updatable = false) // ✅ 수정 불가 필드
    private LocalDateTime createdAt = LocalDateTime.now();
}
