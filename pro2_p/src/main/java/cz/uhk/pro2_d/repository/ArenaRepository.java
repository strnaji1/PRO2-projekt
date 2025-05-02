package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Arena;
import cz.uhk.pro2_d.model.Arena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArenaRepository extends JpaRepository<Arena, Long> {
}
