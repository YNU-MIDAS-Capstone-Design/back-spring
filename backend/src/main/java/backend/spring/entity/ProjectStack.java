package backend.spring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project_stack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ProjectStack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long project_stack_id;

	private String stack;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	public ProjectStack(String stack, Project project){
		this.project = project;
		this.stack = stack;
	}
}
