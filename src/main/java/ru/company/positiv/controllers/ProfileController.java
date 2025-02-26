package ru.company.positiv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ProfileController {

    @GetMapping("/acc") // Личный кабинет пользователя
    public String myAccount(Model model, Principal principal) {
        if (principal != null) {
            String login = principal.getName(); // Получаем логин аутентифицированного пользователя
            model.addAttribute("login", login);
        } else {
            return "redirect:/auth/login";
        }
        return "/personalAccount/myAccount";
    }
}
