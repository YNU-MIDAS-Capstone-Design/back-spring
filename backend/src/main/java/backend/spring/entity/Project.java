package backend.spring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long project_id;

	private String title;
	private String description;
	private String processing;
	private LocalDateTime start_date;
	private Integer project_period;
	private Integer people;
	private String content;

	private String meet_location;
	private Integer project_like;
	private Integer project_view;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime created_at;


	@ManyToOne(fetch = FetchType.LAZY) //작성자 id: 외래 키
	@JoinColumn(name = "user_id")
	private User user;


	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProjectStack> stackList;//프로젝트 스택
	//지원자들
	//댓글
}
