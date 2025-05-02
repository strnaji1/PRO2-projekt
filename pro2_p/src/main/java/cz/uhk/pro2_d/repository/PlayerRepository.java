package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByUsername(String username);
}
