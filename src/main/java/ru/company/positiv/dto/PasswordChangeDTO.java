package ru.company.positiv.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeDTO {

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 6, max = 30, message = "Пароль должен быть в пределах от 6 до 30 символов")
    private String oldPassword;
    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 6, max = 30, message = "Пароль должен быть в пределах от 6 до 30 символов")
    private String newPassword;

}
