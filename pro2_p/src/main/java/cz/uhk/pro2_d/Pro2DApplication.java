package cz.uhk.pro2_d;

import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Pro2DApplication {

    private final PlayerService playerService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Pro2DApplication(PlayerService playerService, PasswordEncoder passwordEncoder) {
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            addPlayer("User", "user", "heslo", "USER");
            addPlayer("Admin", "admin", "heslo", "ADMIN");
        };
    }

    private void addPlayer(String name, String username, String password, String role) {
        Player player = new Player();
        player.setName(name);
        player.setUsername(username);
        player.setPassword(passwordEncoder.encode(password));
        player.setRole(role);
        playerService.savePlayer(player);
    }

    public static void main(String[] args) {
        SpringApplication.run(Pro2DApplication.class, args);
    }
}
