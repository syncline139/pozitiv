package ru.company.positiv.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.company.positiv.dto.PasswordChangeDTO;
import ru.company.positiv.dto.UserDTO;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;
import ru.company.positiv.services.ProfileServices;
import ru.company.positiv.util.PasswordMismatchException;

import java.security.Principal;

/**
 * Контроллер для управления личным кабинетом пользователя.
 * Обрабатывает запросы, связанные с просмотром и обновлением профиля, а также сменой пароля.
 */
@Controller
@RequestMapping("/acc")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileServices profileServices;
    private final UserRepositories userRepositories;
    private final ModelMapper modelMapper;

    /**
     * Отображает страницу личного кабинета пользователя.
     *
     * @param model     объект для передачи данных в шаблон
     * @param principal объект, содержащий информацию о текущем аутентифицированном пользователе
     * @return имя шаблона страницы личного кабинета ("/personalAccount/myAccount")
     */
    @GetMapping
    public String myAccount(Model model, Principal principal) {
        populateModel(model, principal);
        return "/personalAccount/myAccount";
    }

    /**
     * Обрабатывает запрос на обновление данных профиля пользователя.
     * Проверяет валидность данных и сохраняет изменения через сервис.
     *
     * @param userDTO           объект DTO с обновленными данными пользователя
     * @param userBindingResult результат валидации данных
     * @param model             объект для передачи данных в шаблон при ошибке
     * @param principal         объект с информацией о текущем пользователе
     * @return перенаправление на страницу "/acc" при успехе или возврат на страницу профиля при ошибке
     */
    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("userDTO") @Valid UserDTO userDTO,
                                BindingResult userBindingResult,
                                Model model,
                                Principal principal) {
        if (userBindingResult.hasErrors()) {
            populateModel(model, principal);
            return "/personalAccount/myAccount";
        }
        profileServices.save(userDTO);
        return "redirect:/acc";
    }

    /**
     * Обрабатывает запрос на смену пароля пользователя.
     * Проверяет валидность данных и сохраняет новый пароль через сервис.
     *
     * @param passwordChangeDTO     объект DTO с данными для смены пароля
     * @param passwordBindingResult результат валидации данных
     * @param model                 объект для передачи данных в шаблон при ошибке
     * @param principal             объект с информацией о текущем пользователе
     * @return перенаправление на страницу "/acc" при успехе или возврат на страницу профиля при ошибке
     * @throws PasswordMismatchException если старый пароль введен неверно
     */
    @PostMapping("/updatePassword")
    public String updatePassword(@ModelAttribute("passwordChangeDTO") @Valid PasswordChangeDTO passwordChangeDTO,
                                 BindingResult passwordBindingResult,
                                 Model model,
                                 Principal principal) {
        if (passwordBindingResult.hasErrors()) {
            populateModel(model, principal);
            return "/personalAccount/myAccount";
        }
        try {
            profileServices.savePassword(passwordChangeDTO);
        } catch (PasswordMismatchException ex) {
            passwordBindingResult.rejectValue("oldPassword", "error.oldPassword", ex.getMessage());
            populateModel(model, principal);
            return "/personalAccount/myAccount";
        }
        return "redirect:/acc";
    }

    /**
     * Заполняет модель данными текущего пользователя для отображения в шаблоне.
     * Использует ModelMapper для преобразования модели User в UserDTO.
     *
     * @param model     объект для передачи данных в шаблон
     * @param principal объект с информацией о текущем пользователе
     * @throws UsernameNotFoundException если пользователь с данным логином не найден
     */
    private void populateModel(Model model, Principal principal) {
        if (principal != null) {
            User currentUser = userRepositories.findByLogin(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            model.addAttribute("userDTO", modelMapper.map(currentUser, UserDTO.class));
            model.addAttribute("passwordChangeDTO", new PasswordChangeDTO());
            model.addAttribute("login", currentUser.getLogin());
            model.addAttribute("email", currentUser.getEmail());
            model.addAttribute("city", currentUser.getCity());
            model.addAttribute("name", currentUser.getName());
            model.addAttribute("phone", currentUser.getPhone());
        }
    }
}