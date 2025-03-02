package ru.company.positiv.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    // id заказа
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Один пользователь может иметь несколько заказов
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Один продукт может фигурировать в разных заказах
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Дата создания заявки
    @Column(name = "order_date")
    private LocalDateTime orderDate;


    @Column(name = "status")
    private String status;

    @Min(value = 0, message = "Количество не может быть отрицательным")
    @Column(name = "quantity")
    private Long quantity;
}