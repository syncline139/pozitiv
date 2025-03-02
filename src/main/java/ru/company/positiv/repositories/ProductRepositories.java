package ru.company.positiv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.positiv.dto.ProductDTO;
import ru.company.positiv.models.Product;

public interface ProductRepositories extends JpaRepository<Product, Long> {


}
