package ru.company.positiv.dto;



import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDTO {

    // id заказа
    private Long id;

    // Один пользователь может иметь несколько заказов
    private Long userId;

    // Один продукт может фигурировать в разных заказах
    private Long productId;

    // Дата создания заявки
    private LocalDateTime orderDate;
    // Статус заявки «new», «processing», «completed»
    private String status;
}