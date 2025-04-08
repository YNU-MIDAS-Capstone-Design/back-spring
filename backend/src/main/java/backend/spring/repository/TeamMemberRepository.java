package backend.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.spring.entity.Team;
import backend.spring.entity.TeamMember;
import backend.spring.entity.User;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {
	List<TeamMember> findAllByUser(User user);
	List<TeamMember> findAllByTeam(Team team);
}
