package backend.spring.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="proect_like")
@Table(name="project_like")
@IdClass(LikePk.class)
public class ProjectLikeEntity{
    @Id
    private int projectId;
    @Id
    private long user_id;
}
