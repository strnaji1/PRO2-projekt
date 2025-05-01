package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Room;
import cz.uhk.pro2_d.service.CourseService;
import cz.uhk.pro2_d.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private CourseService courseService;

    // ✔️ Opravena cesta aby fungovalo i /rooms/
    @GetMapping({"", "/"})
    public String listRooms(Model model) {
        var rooms = roomService.getAllRooms();
        Map<Long, Integer> roomCourseCount = new HashMap<>();
        for (Room room : rooms) {
            roomCourseCount.put(room.getId(), courseService.countCoursesByRoom(room));
        }

        model.addAttribute("rooms", rooms);
        model.addAttribute("roomCourseCount", roomCourseCount);
        return "rooms_list";
    }

    @GetMapping("/add")
    public String addRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "rooms_add";
    }

    @PostMapping("/save")
    public String saveRoom(@ModelAttribute("room") @Valid Room room,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            return "rooms_add";
        }

        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    @GetMapping("/delete/confirm/{id}")
    public String confirmDeleteRoom(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.getRoomById(id));
        return "rooms_delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/rooms";
    }

    @GetMapping("/edit/{id}")
    public String editRoomForm(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.getRoomById(id));
        return "rooms_add";
    }

    @GetMapping("/{id}")
    public String roomDetail(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.getRoomById(id));
        return "rooms_detail";
    }
}
