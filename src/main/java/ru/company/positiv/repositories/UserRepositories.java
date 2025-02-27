package ru.company.positiv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.company.positiv.dto.UserDTO;
import ru.company.positiv.models.User;

import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select u.email from User u where u.login = :login")
    Optional<String> findEmailByLogin(@Param("login") String login);

    @Query("select u.city from User u where u.login = :login")
    Optional<String> findCityByLogin(@Param("login") String login);

    @Query("select u.name from User u where u.login = :login")
    Optional<String> findNameByLogin(@Param("login") String login);


    Optional<User> findByLogin(String login);


}
