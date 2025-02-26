package ru.company.positiv.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.company.positiv.dto.UserDTO;
import ru.company.positiv.models.User;
import ru.company.positiv.services.RegistrationService;
import ru.company.positiv.util.UserValidator;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration") // Выводит страницу
    public String registrationPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "auth/registration";
    }

    @PostMapping("/registration") //  Авторизируем пользователя
    public String performRegistration(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        userValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("userDTO", userDTO);
            return "auth/registration";
        }
        User user = convertToUser(userDTO);
        registrationService.register(user);
        return "redirect:/auth/login";
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}