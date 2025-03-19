package backend.spring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project_like")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class ProjectLike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long like_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
}
