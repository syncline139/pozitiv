package ru.company.positiv.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.company.positiv.dto.UserDTO;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;
import ru.company.positiv.services.ProfileServices;

import java.security.Principal;

@Controller
@RequestMapping("/acc")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileServices profileServices;
    private final UserRepositories userRepositories;

    @GetMapping // Личный кабинет пользователя
    public String myAccount(User user, Model model, Principal principal) {
        if (principal != null) {
            String login = principal.getName(); // Получаем логин аутентифицированного пользователя
            model.addAttribute("login", login);
            // Добавляем объект userDTO в модель
            model.addAttribute("userDTO", new UserDTO());
            // Почта пользователя ( ищется по логину )
            String email = userRepositories.findEmailByLogin(login).orElse("Быть не может");
            model.addAttribute("email", email);

            String city = userRepositories.findCityByLogin(login).orElse("Не указан");
            model.addAttribute("city", city);

            String name = userRepositories.findNameByLogin(login).orElse("Не указан");
            model.addAttribute("name", name);
        } else {
            return "redirect:/auth/login";
        }
        return "/personalAccount/myAccount";
    }


    @PostMapping
    public String handleAccount(@ModelAttribute("userDTO") UserDTO userDTO,
                                @RequestParam("action") String action) {

        // Если выбрана опция сохранения, сохраняем данные и перенаправляем
        profileServices.save(userDTO);
        return "redirect:/acc";
    }


}