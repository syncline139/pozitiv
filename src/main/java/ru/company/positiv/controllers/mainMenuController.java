package ru.company.positiv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainMenuController {

    @GetMapping
    public String mainScreen() {
        return "/mainScreen";
    }
}
