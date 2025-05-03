package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.repository.PlayerRepository;
import cz.uhk.pro2_d.security.MyPlayerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public void savePlayer(Player player) {
        if (player.getPassword() != null && !player.getPassword().startsWith("$2a$")) {
            player.setPassword(passwordEncoder.encode(player.getPassword()));
        }
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

    @Override
    public Player findByUsername(String username) {
        return playerRepository.findByUsername(username);
    }
}
