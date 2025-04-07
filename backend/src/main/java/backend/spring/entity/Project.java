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
	private Long projectId;

	private String title; //제목
	private String description; //요약
	private String content; //내용

	@Enumerated(EnumType.STRING)
	private Processing processing; //모집중 or 모집완료

	private String recruitmentField;  //여러 개의 모집분야를 하나의 문자열로
	//private LocalDateTime start_date; //시작날짜
	//private Integer project_period; //기간
	private Integer people; //모집중인 인원 수

	@Enumerated(EnumType.STRING)
	private Location meet_location; //오프라인 만남 장소

	private Integer likeCount; //좋아요 수
	private Integer viewCount; //조회수

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime created_at; //프로젝트 생성된 날짜


	@ManyToOne(fetch = FetchType.LAZY) //작성자 id: 외래 키
	@JoinColumn(name = "userId")
	private User user;

	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProjectStack> stackList;//프로젝트 스택

	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProjectComment> commentList;//댓글 리스트

	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProjectLike> likeList; //좋아요한 글

}