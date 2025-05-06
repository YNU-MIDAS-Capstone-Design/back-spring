package backend.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.spring.entity.Team;
import backend.spring.entity.TeamCalendar;

public interface TeamCalendarRepository extends JpaRepository<TeamCalendar,Long> {
	List<TeamCalendar> findAllByTeam(Team team);
}
