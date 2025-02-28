package ru.company.positiv.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.company.positiv.dto.PasswordChangeDTO;
import ru.company.positiv.dto.UserDTO;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;
import ru.company.positiv.util.PasswordMismatchException;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServicesImpl implements ProfileServices {

    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(UserDTO userDTO) {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepositories.findByLogin(currentLogin)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        System.out.println("Текущий пользователь: " + user.getLogin());
        System.out.println("Новое имя из DTO: " + userDTO.getName());
        System.out.println("Новый город из DTO: " + userDTO.getCity());
        user.setCity(userDTO.getCity());
        user.setName(userDTO.getName());
        userRepositories.save(user);
        System.out.println("Сохраненный пользователь: " + user);
    }

    /*
     *  сравниваем введнный старый пароль с тем который ъраниться в бд
     * если все в порядке шифруем новый пароль и сохраняем
     */
    @Override
    public void savePassword(PasswordChangeDTO passwordChangeDTO) {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepositories.findByLogin(currentLogin)
                .orElseThrow(() -> new UsernameNotFoundException("Пользовательно не найден"));
        if (!passwordEncoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
            throw new PasswordMismatchException("Старый пароль указан неверно!");
        }
        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        userRepositories.save(user);
    }
}
