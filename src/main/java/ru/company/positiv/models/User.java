package ru.company.positiv.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // id пользователя
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Имя пользователя ( оно не являеться обязательным и устанавливаеться по желанию в личном кабинете)
    @Column(name = "name")
    @Size(min = 2, max = 30, message = "Имя должно быть в пределах от 2 до 30")
    private String name;

    // логин пользователя
    @Column(name = "login")
    @NotEmpty(message = "Логин не должен быть пустым")
    @Size(min = 4, max = 30,message = "Логин должен быть в пределах от 4 до 30 символов")
    private String login;

    // email пользователя
    @Column(name = "email")
    @Email(message = "Введите корректную почту")
    private String email;

    // Город пользователя ( оно не являеться обязательным и устанавливаеться по желанию в личном кабинете)
    @Column(name = "city")
    private String city;

    // Послдений заход пользователя на сайт
    @Column(name = "last_connect")
    private LocalDateTime lastConnect;

    // Пароль пользователя
    @Column(name = "password")
    @Size(min = 6, max = 30, message = "Пароль должен быть в пределах от 6 до 30 символов")
    private String password;


    // JWT refresh_token
    @Column(name = "refresh_token")
    private String refreshToken;
}