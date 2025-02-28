package ru.company.positiv.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.company.positiv.dto.RegistrationDTO;
import ru.company.positiv.dto.UserDTO;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;
import ru.company.positiv.services.AuthService;
import ru.company.positiv.util.UserValidator;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserValidator userValidator;
    private final AuthService authService;
    private final ModelMapper modelMapper;
    private final UserRepositories userRepositories;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }


    @GetMapping("/registration") // Выводит страницу
    public String registrationPage(Model model) {
        model.addAttribute("registrationDTO", new RegistrationDTO());
        return "auth/registration";
    }

    @PostMapping("/registration") //  Авторизируем пользователя
    public String performRegistration(@Valid RegistrationDTO registrationDTO, BindingResult bindingResult, Model model) {
        userValidator.validate(registrationDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationDTO", registrationDTO);
            return "auth/registration";
        }
        User user = convertToUser(registrationDTO);
        authService.register(user);
        return "redirect:/auth/login";
    }

    private User convertToUser(RegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }


}