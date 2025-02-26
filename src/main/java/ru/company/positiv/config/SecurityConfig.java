package ru.company.positiv.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.company.positiv.repositories.UserRepositories;
import ru.company.positiv.services.CustomUserDetailsService;

import java.time.LocalDateTime;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final UserRepositories userRepositories;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Шифрование пароля
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/").permitAll() // Разрешаем доступ к страницам аутентификации
                        .anyRequest().authenticated() // Все остальное требует аутентификации
                )
                .formLogin(form -> form
                        .loginPage("/") // мЕНЮ
                        .loginProcessingUrl("/process_login") // URL обработки логина
                        .successHandler((request, response, authentication) -> {
                            Object principal = authentication.getPrincipal(); // После успешного входа в бд обновляеться значение последнего входа
                            if (principal instanceof UserDetails userDetails) {
                                userRepositories.findByLogin(userDetails.getUsername()).ifPresent(user -> {
                                    user.setLastConnect(LocalDateTime.now());
                                    userRepositories.save(user);
                                });
                            }
                            response.sendRedirect("/");
                        })
                        .failureUrl("/auth/login?error=true") // Если ошибка
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL выхода
                        .logoutSuccessUrl("/auth/login") // После выхода
                        .invalidateHttpSession(true) // Очистка сессии
                        .deleteCookies("JSESSIONID") // Удаление куки
                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
