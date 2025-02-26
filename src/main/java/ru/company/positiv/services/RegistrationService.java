package ru.company.positiv.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;


@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(User user) {
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("USER");
        userRepositories.save(user);
    }
}
