package ru.company.positiv.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "Имя должно быть в пределах от 2 до 30")
    private String name;

    @Column(name = "login")
    @NotEmpty(message = "Логин не должен быть пустым")
    @Size(min = 4, max = 30, message = "Логин должен быть в пределах от 4 до 30 символов")
    private String login;

    @Column(name = "email")
    @Email(message = "Введите корректную почту")
    private String email;

    @Column(name = "city")
    private String city;

    @Column(name = "last_connect")
    private LocalDateTime lastConnect;

    @Column(name = "password")
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

    @Column(name = "role")
    private String role;
    @Column(name = "registered_at")
    private LocalDateTime registeredAt;
}