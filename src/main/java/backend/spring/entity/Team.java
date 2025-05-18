package backend.spring.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	private Long teamId;

	private String teamName;
	private String teamImage;
	private String teamColor;

	//리스트들
	@OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
	private List<TeamMember> memberList; //멤버 리스트

	@OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
	private List<TeamCalendar> calendarList; //팀 일정 리스트

	public Team(String teamName, String teamImage, String teamColor){
		this.teamName = teamName;
		this.teamImage = teamImage;
		this.teamColor = teamColor;
	}
}
