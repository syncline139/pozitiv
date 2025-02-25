package ru.company.positiv.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;

    @NotEmpty(message = "Введите название продукта")
    private String name;
    // Описание ( может хранить очень много текста )
    private String description;
    // Количество товара на складе
    private Long quantity;
}