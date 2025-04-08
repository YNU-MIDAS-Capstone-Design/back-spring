package backend.spring.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ProjectComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	private String message;
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime created_at; //댓글 생성 날짜

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectId")
	private Project project;

	@ManyToOne(fetch = FetchType.LAZY) //작성자 id: 외래 키
	@JoinColumn(name = "userId")
	private User user;
}
