package backend.spring.entity;

import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.Processing;
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

	private String title; //제목
	private String description; //요약

	@Enumerated(EnumType.STRING)
	private Processing processing; //모집중 or 모집완료

	private LocalDateTime start_date; //시작날짜
	private Integer project_period; //기간
	private Integer people; //모집중인 인원 수
	private String content; //내용

	@Enumerated(EnumType.STRING)
	private Location meet_location; //오프라인 만남 장소

	private Integer project_like; //좋아요 수
	private Integer project_view; //조회수

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime created_at; //프로젝트 생성된 날짜


	@ManyToOne(fetch = FetchType.LAZY) //작성자 id: 외래 키
	@JoinColumn(name = "user_id")
	private User user;


	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProjectStack> stackList;//프로젝트 스택
	//지원자들
	//댓글
}