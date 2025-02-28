package ru.company.positiv.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    private Long id;

    @Size(min = 2, max = 30, message = "Имя должно быть в пределах от 2 до 30")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\s\\-]+$", message = "Некорректное имя")
    private String name;

    @NotEmpty(message = "Логин не должен быть пустым")
    private String login;

    @Email(message = "Введите корректную почту")
    private String email;
    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 6, max = 30, message = "Пароль должен быть в пределах от 6 до 30 символов")
    private String password;
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\d/\\s.\\-]+$", message = "Некорректный адрес")
    private String city;
}
