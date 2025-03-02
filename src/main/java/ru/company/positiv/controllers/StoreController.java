package ru.company.positiv.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.company.positiv.models.Order;
import ru.company.positiv.models.Product;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.OrderRepositories;
import ru.company.positiv.repositories.ProductRepositories;
import ru.company.positiv.repositories.UserRepositories;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final ProductRepositories productRepository;
    private final UserRepositories userRepositories;
    private final OrderRepositories orderRepositories;

    // Показать все товары
    @GetMapping
    public String showAllProducts(Model model) {
        List<Product> products = productRepository.findAll();

        model.addAttribute("products", products);

        return "store/storePage";
    }

    @PostMapping("/order")
    public String orderProduct(
            @RequestParam("productId") Long productId,
            @RequestParam("orderQuantity") Long orderQuantity,
            Principal principal
    ) {
        if (principal == null) {
            return "redirect:/login";
        }

        User currentUser = userRepositories.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));

        if (product.getQuantity() < orderQuantity) {
            throw new RuntimeException("Недостаточно товара на складе");
        }

        product.setQuantity(product.getQuantity() - orderQuantity);
        productRepository.save(product);

        Order order = new Order();
        order.setUser(currentUser);
        order.setProduct(product);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("new"); // Изначальный статус

        order.setQuantity(orderQuantity);

        orderRepositories.save(order);

        return "redirect:/store";
    }


}
