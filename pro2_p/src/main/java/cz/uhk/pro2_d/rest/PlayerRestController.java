package cz.uhk.pro2_d.rest;

import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PlayerRestController {

    private final PlayerService playerService;

    @Autowired
    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/rest/players/all")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/rest/players/get/{id}")
    public Player getPlayer(@PathVariable long id) {
        return playerService.getPlayer(id);
    }

    @PostMapping("/rest/players/new")
    public String newPlayer(@ModelAttribute Player player) {
        playerService.savePlayer(player);
        return player.toString();
    }

    @PutMapping("/rest/players/update/{id}")
    public String updatePlayer(@ModelAttribute Player player) {
        // co když player neexistuje?
        playerService.savePlayer(player);
        return player.toString();
    }

    @DeleteMapping("/rest/players/delete/{id}")
    public String deletePlayer(@PathVariable long id) {
        playerService.deletePlayer(id);
        return "ok";
    }
}
