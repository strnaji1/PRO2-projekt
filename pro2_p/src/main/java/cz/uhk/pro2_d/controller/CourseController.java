package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Course;
import cz.uhk.pro2_d.service.CourseService;
import cz.uhk.pro2_d.service.LecturerService;
import cz.uhk.pro2_d.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final LecturerService lecturerService;
    private final RoomService roomService;

    @Autowired
    public CourseController(CourseService courseService, LecturerService lecturerService, RoomService roomService) {
        this.courseService = courseService;
        this.lecturerService = lecturerService;
        this.roomService = roomService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        model.addAttribute("course", courseService.getCourse(id));
        return "courses_detail";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("lecturers", lecturerService.getAllLecturers());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "courses_add";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("course", courseService.getCourse(id));
        model.addAttribute("lecturers", lecturerService.getAllLecturers());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "courses_add";
    }

    @PostMapping("/save")
    public String addSave(@ModelAttribute("course") @Valid Course course,
                          BindingResult result,
                          Model model) {

        // Custom validace: účastníci ≤ kapacita místnosti
        if (course.getRoom() != null && course.getParticipants() > course.getRoom().getCapacity()) {
            result.rejectValue("participants", "error.course", "Počet účastníků překračuje kapacitu místnosti");
        }

        if (result.hasErrors()) {
            model.addAttribute("lecturers", lecturerService.getAllLecturers());
            model.addAttribute("rooms", roomService.getAllRooms());
            return "courses_add";
        }

        courseService.saveCourse(course);
        return "redirect:/courses/";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable long id) {
        model.addAttribute("course", courseService.getCourse(id));
        return "courses_delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable long id) {
        courseService.deleteCourse(id);
        return "redirect:/courses/";
    }
}
