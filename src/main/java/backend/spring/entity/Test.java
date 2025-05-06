package backend.spring.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "test")
@Getter //게터로 getId, getTitle, getContent 사용 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED)//producted타입으로 기본생성자
public class Test {

}
