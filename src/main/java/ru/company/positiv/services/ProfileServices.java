package ru.company.positiv.services;

import ru.company.positiv.dto.PasswordChangeDTO;
import ru.company.positiv.dto.UserDTO;

public interface ProfileServices {
    void save(UserDTO userDTO);

    void savePassword(PasswordChangeDTO passwordChangeDTO);
}
