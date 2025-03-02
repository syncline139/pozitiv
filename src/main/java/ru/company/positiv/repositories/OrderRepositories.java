package ru.company.positiv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.company.positiv.models.Order;

import java.util.List;

@Repository
public interface OrderRepositories extends JpaRepository<Order, Long> {

    // все заказы конкретного пользователя
    List<Order> findByUserId(Long userId);


}
