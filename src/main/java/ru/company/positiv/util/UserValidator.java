package ru.company.positiv.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.company.positiv.dto.RegistrationDTO;
import ru.company.positiv.dto.UserDTO;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;

@RequiredArgsConstructor
@Component
public class UserValidator implements Validator {

    private final UserRepositories userRepositories;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationDTO registrationDTO = (RegistrationDTO) target;
        System.out.println("Валидатор вызван для объекта: " + target);
        if (userRepositories.findByEmail(registrationDTO.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Данная электронная почта уже занята другим пользователем!");
        }
        if (userRepositories.findByLogin(registrationDTO.getLogin()).isPresent()) {
            errors.rejectValue("login", "", "Данный логин уже занят другим пользователем!");
        }


    }
}
