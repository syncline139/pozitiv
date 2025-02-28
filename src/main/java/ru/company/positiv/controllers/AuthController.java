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

/**
 * Контроллер для управления процессами аутентификации и регистрации пользователей.
 * Обрабатывает запросы на отображение страницы логина, страницы регистрации и выполнение регистрации.
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserValidator userValidator;
    private final AuthService authService;
    private final ModelMapper modelMapper;
    private final UserRepositories userRepositories;

    /**
     * Отображает страницу входа в систему.
     *
     * @return имя шаблона страницы логина ("auth/login")
     */
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /**
     * Отображает страницу регистрации нового пользователя.
     * Добавляет пустой объект RegistrationDTO в модель для заполнения формы.
     *
     * @param model объект для передачи данных в шаблон
     * @return имя шаблона страницы регистрации ("auth/registration")
     */
    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("registrationDTO", new RegistrationDTO());
        return "auth/registration";
    }

    /**
     * Обрабатывает запрос на регистрацию нового пользователя.
     * Проверяет данные на валидность и регистрирует пользователя через сервис.
     *
     * @param registrationDTO объект DTO с данными для регистрации
     * @param bindingResult   результат валидации данных
     * @param model           объект для передачи данных в шаблон при ошибке
     * @return перенаправление на страницу "/auth/login" при успехе или возврат на страницу регистрации при ошибке
     */
    @PostMapping("/registration")
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

    /**
     * Преобразует объект RegistrationDTO в сущность User с использованием ModelMapper.
     *
     * @param registrationDTO объект DTO с данными регистрации
     * @return объект User, готовый для сохранения в базе данных
     */
    private User convertToUser(RegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }
}