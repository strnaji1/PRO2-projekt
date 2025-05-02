package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Referee;
import cz.uhk.pro2_d.service.RefereeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/referees")
public class RefereeController {

    private RefereeService refereeService;

    @Autowired
    public RefereeController(RefereeService refereeService) {
        this.refereeService = refereeService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("referees", refereeService.getAllReferees());
        return "referees_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        model.addAttribute("referee", refereeService.getReferee(id));
        return "referees_detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("referee", new Referee());
        return "referees_add";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("referee", refereeService.getReferee(id));
        return "referees_add";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String addSave(@ModelAttribute Referee referee) {
        refereeService.saveReferee(referee);
        return "redirect:/referees/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable long id) {
        model.addAttribute("referee", refereeService.getReferee(id));
        return "referees_delete";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable long id) {
        refereeService.deleteReferee(id);
        return "redirect:/referees/";
    }
}
