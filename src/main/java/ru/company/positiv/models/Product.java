package ru.company.positiv.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    // id продукта
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //  Название продукта
    @Column(name = "name")
    @NotEmpty(message = "Введите название продукта")
    private String name;

    // Описание ( может хранить очень много текста )
    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Количество товара на складе
    @Column(name = "quantity")
    private Long quantity;
}
