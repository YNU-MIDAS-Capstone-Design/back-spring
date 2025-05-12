package backend.spring.entity;

import backend.spring.entity.enums.Stack;
import jakarta.persistence.*;
		import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.stream.Collectors;

@Entity
@Table(name = "project_stack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ProjectStack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectStackId;

	@Enumerated(EnumType.STRING)
	private Stack stack;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectId")
	private Project project;

	public ProjectStack(Stack stack, Project project){
		this.project = project;
		this.stack = stack;
	}
}