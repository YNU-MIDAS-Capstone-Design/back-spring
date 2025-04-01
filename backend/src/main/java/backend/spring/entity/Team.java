package backend.spring.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "team")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long team_id;

	private String team_name;

	@OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<TeamMember> memberList;
}
