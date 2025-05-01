package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.service.CourseService;
import cz.uhk.pro2_d.service.LecturerService;
import cz.uhk.pro2_d.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    private final CourseService courseService;
    private final LecturerService lecturerService;
    private final RoomService roomService;


    @Autowired
    public IndexController(CourseService courseService, LecturerService lecturerService, RoomService roomService) {
        this.courseService = courseService;
        this.lecturerService = lecturerService;
        this.roomService = roomService;
    }
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("courseCount", courseService.getAllCourses().size());
        model.addAttribute("lecturerCount", lecturerService.getAllLecturers().size());
        model.addAttribute("roomCount", roomService.getAllRooms().size());
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
