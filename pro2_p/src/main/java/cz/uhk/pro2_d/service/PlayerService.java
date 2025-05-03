package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Player;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface PlayerService extends UserDetailsService {
    List<Player> getAllPlayers();
    void savePlayer(Player player);
    Player getPlayer(long id);
    void deletePlayer(long id);
    Player findByUsername(String username); // doplněno pro MatchController
}
