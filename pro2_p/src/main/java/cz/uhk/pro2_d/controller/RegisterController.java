package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final PlayerService playerService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(PlayerService playerService, PasswordEncoder passwordEncoder) {
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("player", new Player());
        return "register";
    }

    @PostMapping("/register")
    public String registerPlayer(@Valid Player player, BindingResult result) {
        if (playerService.findByUsername(player.getUsername()) != null) {
            result.rejectValue("username", "error.player", "Uživatelské jméno již existuje.");
        }

        if (result.hasErrors()) {
            return "register";
        }

        player.setPassword(passwordEncoder.encode(player.getPassword()));
        player.setRole("USER");
        playerService.savePlayer(player);
        return "redirect:/login?registered";
    }
}
