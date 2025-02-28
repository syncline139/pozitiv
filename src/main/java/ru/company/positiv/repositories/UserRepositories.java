package ru.company.positiv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.company.positiv.models.User;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью User в базе данных.
 * Предоставляет методы для поиска пользователей по различным критериям.
 */
@Repository
public interface UserRepositories extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по его электронной почте.
     */
    Optional<User> findByEmail(String email);

    /**
     * Находит email пользователя по его логину.
     */
    @Query("select u.email from User u where u.login = :login")
    Optional<String> findEmailByLogin(@Param("login") String login);

    /**
     * Находит город проживания пользователя по его логину.
     */
    @Query("select u.city from User u where u.login = :login")
    Optional<String> findCityByLogin(@Param("login") String login);

    /**
     * Находит имя пользователя по его логину.
     */
    @Query("select u.name from User u where u.login = :login")
    Optional<String> findNameByLogin(@Param("login") String login);

    /**
     * Находит пользователя по его логину.
     */
    Optional<User> findByLogin(String login);
}