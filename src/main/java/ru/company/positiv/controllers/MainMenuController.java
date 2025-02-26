package ru.company.positiv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class MainMenuController {

    @GetMapping // Главный экран
    public String mainScreen(Model model) {
        // Здесь можно, например, добавить пустой объект UserDTO, если требуется
        model.addAttribute("login", new Object()); // или убрать, если не нужен
        return "/mainScreen";
    }

}
