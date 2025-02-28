package ru.company.positiv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class MainMenuController {

    @GetMapping // Главный экран
    public String mainScreen(Model model) {
        model.addAttribute("login", new Object());
        return "/mainScreen";
    }

}
