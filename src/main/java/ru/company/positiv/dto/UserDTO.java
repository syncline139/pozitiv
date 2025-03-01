package ru.company.positiv.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;

    @Size(min = 2, max = 30, message = "Имя должно быть в пределах от 2 до 30")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\s\\-]+$", message = "Некорректное имя")
    private String name;

    private String login;

    @Email(message = "Введите корректную почту")
    private String email;

    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\d/\\s.\\-]+$", message = "Некорректный адрес")
    private String city;

    @Column(name = "phone")
    private String phone;


}