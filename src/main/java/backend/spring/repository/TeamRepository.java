package backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.spring.entity.Team;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
