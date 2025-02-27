package ru.company.positiv.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.company.positiv.dto.UserDTO;
import ru.company.positiv.repositories.UserRepositories;
import ru.company.positiv.services.ProfileServices;

import java.security.Principal;

@Controller
@RequestMapping("/acc")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileServices profileServices;

    @GetMapping // Личный кабинет пользователя
    public String myAccount(Model model, Principal principal) {
        if (principal != null) {
            String login = principal.getName(); // Получаем логин аутентифицированного пользователя
            model.addAttribute("login", login);
            // Добавляем объект userDTO в модель
            model.addAttribute("userDTO", new UserDTO());
        } else {
            return "redirect:/auth/login";
        }
        return "/personalAccount/myAccount";
    }


    @PostMapping
    public String handleAccount(@ModelAttribute("userDTO") UserDTO userDTO,
                                @RequestParam("action") String action) {
        if ("cancel".equals(action)) {
            // Откат изменений и перенаправление на страницу личного кабинета
            return "redirect:/acc";
        }
        // Если выбрана опция сохранения, сохраняем данные и перенаправляем
        profileServices.save(userDTO);
        return "redirect:/acc";
    }


}
