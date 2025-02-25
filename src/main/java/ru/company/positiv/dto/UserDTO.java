package ru.company.positiv.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;
    // Имя пользователя ( оно не являеться обязательным и устанавливаеться по желанию в личном кабинете)
    @Size(min = 2, max = 30, message = "Имя должно быть в пределах от 2 до 30")
    private String name;

    @NotEmpty(message = "Логин не должен быть пустым")
    private String login;

    @Email(message = "Введите корректную почту")
    private String email;

    // Город пользователя ( оно не являеться обязательным и устанавливаеться по желанию в личном кабинете)
    private String city;
}