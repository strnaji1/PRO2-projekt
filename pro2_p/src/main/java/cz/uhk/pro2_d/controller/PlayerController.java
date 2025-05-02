package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("players", playerService.getAllPlayers());
        return "players_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        model.addAttribute("player", playerService.getPlayer(id));
        return "players_detail";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("player", new Player());
        return "players_add";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("player", playerService.getPlayer(id));
        return "players_add";
    }

    @PostMapping("/save")
    public String addSave(@ModelAttribute Player player) {
        playerService.savePlayer(player);
        return "redirect:/players/";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable long id) {
        model.addAttribute("player", playerService.getPlayer(id));
        return "players_delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable long id) {
        playerService.deletePlayer(id);
        return "redirect:/players/";
    }
}
