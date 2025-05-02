package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.service.MatchService;
import cz.uhk.pro2_d.service.RefereeService;
import cz.uhk.pro2_d.service.ArenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    private final MatchService matchService;
    private final RefereeService refereeService;
    private final ArenaService arenaService;

    @Autowired
    public IndexController(MatchService matchService, RefereeService refereeService, ArenaService arenaService) {
        this.matchService = matchService;
        this.refereeService = refereeService;
        this.arenaService = arenaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("matchCount", matchService.getAllMatches().size());
        model.addAttribute("refereeCount", refereeService.getAllReferees().size());
        model.addAttribute("arenaCount", arenaService.getAllArenas().size());
        return "index";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }
}
