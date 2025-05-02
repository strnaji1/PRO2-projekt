package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Arena;
import cz.uhk.pro2_d.service.MatchService;
import cz.uhk.pro2_d.service.ArenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/arenas")
public class ArenaController {

    @Autowired
    private ArenaService arenaService;

    @Autowired
    private MatchService matchService;

    @GetMapping({"", "/"})
    public String listArenas(Model model) {
        var arenas = arenaService.getAllArenas();
        Map<Long, Integer> arenaMatchCount = new HashMap<>();
        for (Arena arena : arenas) {
            arenaMatchCount.put(arena.getId(), matchService.countMatchesByArena(arena));
        }

        model.addAttribute("arenas", arenas);
        model.addAttribute("arenaMatchCount", arenaMatchCount);
        return "arenas_list";
    }

    @GetMapping("/add")
    public String addArenaForm(Model model) {
        model.addAttribute("arena", new Arena());
        return "arenas_add";
    }

    @PostMapping("/save")
    public String saveArena(@ModelAttribute("arena") @Valid Arena arena,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            return "arenas_add";
        }

        arenaService.saveArena(arena);
        return "redirect:/arenas";
    }

    @GetMapping("/delete/confirm/{id}")
    public String confirmDeleteArena(@PathVariable Long id, Model model) {
        model.addAttribute("arena", arenaService.getArenaById(id));
        return "arenas_delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteArena(@PathVariable Long id) {
        arenaService.deleteArena(id);
        return "redirect:/arenas";
    }

    @GetMapping("/edit/{id}")
    public String editArenaForm(@PathVariable Long id, Model model) {
        model.addAttribute("arena", arenaService.getArenaById(id));
        return "arenas_add";
    }

    @GetMapping("/{id}")
    public String arenaDetail(@PathVariable Long id, Model model) {
        model.addAttribute("arena", arenaService.getArenaById(id));
        return "arenas_detail";
    }
}
