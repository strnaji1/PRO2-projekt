package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Match;
import cz.uhk.pro2_d.service.MatchService;
import cz.uhk.pro2_d.service.RefereeService;
import cz.uhk.pro2_d.service.ArenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    public MatchController(MatchService matchService, RefereeService refereeService, ArenaService arenaService) {
        this.matchService = matchService;
        this.refereeService = refereeService;
        this.arenaService = arenaService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("matches", matchService.getAllMatches());
        return "matches_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        model.addAttribute("match", matchService.getMatch(id));
        return "matches_detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("match", new Match());
        model.addAttribute("referees", refereeService.getAllReferees());
        model.addAttribute("arenas", arenaService.getAllArenas());
        return "matches_add";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("match", matchService.getMatch(id));
        model.addAttribute("referees", refereeService.getAllReferees());
        model.addAttribute("arenas", arenaService.getAllArenas());
        return "matches_add";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String addSave(@ModelAttribute("match") @Valid Match match,
                          BindingResult result,
                          Model model) {

        if (match.getArena() != null && match.getParticipants() > match.getArena().getCapacity()) {
            result.rejectValue("participants", "error.match", "Počet hráčů překračuje kapacitu arény");
        }

        if (result.hasErrors()) {
            model.addAttribute("referees", refereeService.getAllReferees());
            model.addAttribute("arenas", arenaService.getAllArenas());
            return "matches_add";
        }

        matchService.saveMatch(match);
        return "redirect:/matches/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable long id) {
        model.addAttribute("match", matchService.getMatch(id));
        return "matches_delete";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable long id) {
        matchService.deleteMatch(id);
        return "redirect:/matches/";
    }
}
