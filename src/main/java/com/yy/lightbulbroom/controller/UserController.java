package com.yy.lightbulbroom.controller;

import com.yy.lightbulbroom.exception.RoomNotFoundException;
import com.yy.lightbulbroom.model.Country;
import com.yy.lightbulbroom.model.Room;
import com.yy.lightbulbroom.repository.CountryRepository;
import com.yy.lightbulbroom.repository.RoomRepository;
import com.yy.lightbulbroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final RoomRepository roomRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public UserController(UserService userService, RoomRepository roomRepository, CountryRepository countryRepository) {
        this.userService = userService;
        this.roomRepository = roomRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/")
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping("/room/allRooms")
    public String allRooms(Model model) {
        List<Room> allRooms = userService.findAllRooms();
        model.addAttribute("allRooms", allRooms);
        return "allRooms";
    }

    @PostMapping("/")
    public String editPage(@ModelAttribute("room") Room room) {
        roomRepository.save(room);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String checkRoom(@PathVariable Long id, Model model) throws RoomNotFoundException, IOException, AccessException {
        Room room = userService.checkAccessToTheRoom(id);
        model.addAttribute("room", room);
        return "editPage";
    }

    @PatchMapping("/edit/{id}")
    public String editLight(@PathVariable Long id, @ModelAttribute("room") Room room) {
        userService.updateRoom(room);
        return "redirect:/edit/{id}";
    }

    @GetMapping("/room/new")
    public String createRoomGet(Model model) {
        List<Country> countryList = countryRepository.findAll();
        model.addAttribute("room", new Room());
        model.addAttribute("allCountries", countryList);
        return "creatingRoomForm";
    }

    @PostMapping("/room/new")
    public String createRoomPost(@ModelAttribute("room") Room room) {
        roomRepository.save(room);
        return "redirect:/";
    }
}
