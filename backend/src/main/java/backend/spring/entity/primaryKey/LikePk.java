package backend.spring.entity.primaryPk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LikePk implements Serializable {
    @Column(name = "user_id")
    private long userId;
    @Column(name = "project_id")
    private int projectId;
}
