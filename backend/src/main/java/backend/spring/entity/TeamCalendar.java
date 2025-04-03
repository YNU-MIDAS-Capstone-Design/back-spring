package backend.spring.entity;

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
@Table(name = "team_calendar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class TeamCalendar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cal_id;

	private String content; //일정 내용

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;
}
