package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Match;
import cz.uhk.pro2_d.model.Player;
import cz.uhk.pro2_d.service.MatchService;
import cz.uhk.pro2_d.service.RefereeService;
import cz.uhk.pro2_d.service.ArenaService;
import cz.uhk.pro2_d.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;
    private final RefereeService refereeService;
    private final ArenaService arenaService;
    private final PlayerService playerService;

    @Autowired
    public MatchController(MatchService matchService, RefereeService refereeService, ArenaService arenaService, PlayerService playerService) {
        this.matchService = matchService;
        this.refereeService = refereeService;
        this.arenaService = arenaService;
        this.playerService = playerService;
    }

    @GetMapping("/")
    public String list(Model model, Authentication auth) {
        model.addAttribute("matches", matchService.getAllMatches());
        model.addAttribute("currentPlayer", playerService.findByUsername(auth.getName()));
        return "matches_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id, Authentication auth) {
        Match match = matchService.getMatch(id);
        Player currentPlayer = playerService.findByUsername(auth.getName());

        model.addAttribute("match", match);
        model.addAttribute("currentPlayer", currentPlayer);
        model.addAttribute("isRegistered", match.getPlayers().contains(currentPlayer));
        model.addAttribute("full", match.getPlayers().size() >= match.getArena().getCapacity());

        return "matches_detail";
    }

    @PostMapping("/{id}/register")
    public String registerToMatch(@PathVariable long id, Authentication auth) {
        Match match = matchService.getMatch(id);
        Player currentPlayer = playerService.findByUsername(auth.getName());

        if (!match.getPlayers().contains(currentPlayer)
                && match.getPlayers().size() < match.getArena().getCapacity()) {
            match.getPlayers().add(currentPlayer);
            match.setParticipants(match.getPlayers().size());
            matchService.saveMatch(match);
        }

        return "redirect:/matches/" + id;
    }

    @PostMapping("/{id}/unregister")
    public String unregisterFromMatch(@PathVariable long id, Authentication auth) {
        Match match = matchService.getMatch(id);
        Player currentPlayer = playerService.findByUsername(auth.getName());

        if (match.getPlayers().contains(currentPlayer)) {
            match.getPlayers().remove(currentPlayer);
            match.setParticipants(match.getPlayers().size());
            matchService.saveMatch(match);
        }

        return "redirect:/matches/" + id;
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("match", new Match());
        model.addAttribute("referees", refereeService.getAllReferees());
        model.addAttribute("arenas", arenaService.getAllArenas());
        model.addAttribute("players", playerService.getAllPlayers());
        return "matches_add";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("match", matchService.getMatch(id));
        model.addAttribute("referees", refereeService.getAllReferees());
        model.addAttribute("arenas", arenaService.getAllArenas());
        model.addAttribute("players", playerService.getAllPlayers());
        return "matches_add";
    }

    @PostMapping("/save")
    public String addSave(@ModelAttribute("match") @Valid Match match,
                          BindingResult result,
                          Model model) {

        if (match.getArena() != null && match.getParticipants() > match.getArena().getCapacity()) {
            result.rejectValue("participants", "error.match", "Počet hráčů překračuje kapacitu arény");
        }

        if (match.getPlayers() != null && match.getArena() != null && match.getPlayers().size() > match.getArena().getCapacity()) {
            result.rejectValue("players", "error.match", "Počet přihlášených hráčů překračuje kapacitu arény");
        }

        if (result.hasErrors()) {
            model.addAttribute("referees", refereeService.getAllReferees());
            model.addAttribute("arenas", arenaService.getAllArenas());
            model.addAttribute("players", playerService.getAllPlayers());
            return "matches_add";
        }

        match.setParticipants(match.getPlayers() != null ? match.getPlayers().size() : 0);
        matchService.saveMatch(match);
        return "redirect:/matches/";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable long id) {
        model.addAttribute("match", matchService.getMatch(id));
        return "matches_delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable long id) {
        matchService.deleteMatch(id);
        return "redirect:/matches/";
    }
}
