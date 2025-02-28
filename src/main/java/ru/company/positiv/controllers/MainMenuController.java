package ru.company.positiv.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainMenuController {
    private final UserRepositories userRepositories;

    @GetMapping // Главный экран
    public String mainScreen(Model model, Principal principal) {
        if (principal != null) {
            String login = principal.getName();
            User user = userRepositories.findByLogin(login)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            model.addAttribute("user", user);
        } else {
            // Генерация случайной буквы (от 'A' до 'Z')
            char randomLetter = (char) ('A' + new java.util.Random().nextInt(26));
            model.addAttribute("randomLetter", randomLetter);
        }
        return "mainScreen";
    }





}
