package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Player;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerService extends UserDetailsService {
    List<Player> getAllPlayers();
    void savePlayer(Player player);
    Player getPlayer(long id);
    void deletePlayer(long id);
}
