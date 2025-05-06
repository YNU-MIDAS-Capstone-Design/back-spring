package backend.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "test")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Test {
}
