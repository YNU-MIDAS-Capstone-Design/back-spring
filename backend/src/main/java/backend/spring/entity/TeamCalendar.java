package backend.spring.entity;

import java.time.LocalDateTime;

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
	private Long calId;

	private LocalDateTime cal_date; //날짜
	private String content; //일정 내용

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teamId")
	private Team team;

	public TeamCalendar(LocalDateTime cal_date, String content, Team team){
		this.cal_date = cal_date;
		this.content = content;
		this.team = team;
	}
}
