package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.repository.PlayerRepository;
import cz.uhk.pro2_d.security.MyPlayerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public void savePlayer(Player player) {
        // TODO: přidat hashování hesla
        playerRepository.save(player);
    }

    @Override
    public Player getPlayer(long id) {
        return playerRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePlayer(long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = playerRepository.findByUsername(username);
        if (player == null) {
            throw new UsernameNotFoundException("Player not found");
        }
        return new MyPlayerDetails(player);
    }
}
