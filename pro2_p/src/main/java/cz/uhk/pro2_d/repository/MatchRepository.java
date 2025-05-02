package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Match;
import cz.uhk.pro2_d.model.Arena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findAllByArena(Arena arena);
}
