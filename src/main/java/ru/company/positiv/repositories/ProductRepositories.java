package ru.company.positiv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.company.positiv.dto.ProductDTO;
import ru.company.positiv.models.Product;
@Repository
public interface ProductRepositories extends JpaRepository<Product, Long> {


}
