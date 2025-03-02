package ru.company.positiv.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;


import java.security.Principal;

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

            char randomLetter = (char) ('A' + new java.util.Random().nextInt(26));
            model.addAttribute("randomLetter", randomLetter);
        }
        return "mainScreen";
    }

    @GetMapping("/logout") // Обработка выхода
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {

            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
    }
}