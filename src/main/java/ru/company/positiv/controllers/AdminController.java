package ru.company.positiv.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.company.positiv.models.Product;
import ru.company.positiv.repositories.ProductRepositories;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductRepositories productRepository;

    @GetMapping
    public String showAdminPage(@ModelAttribute("product") Product product) {
        // Возвращаем страницу с формой добавления товаров
        return "/store/adminStore";
    }

    @PostMapping("/add")
    public String addProducts(
            @ModelAttribute("product") @Valid Product product,
            BindingResult bindingResult
    ) {
        // Если при валидации возникли ошибки, снова возвращаем страницу с формой
        if (bindingResult.hasErrors()) {
            return "/store/adminStore";
        }

        // Сохраняем товар в БД
        productRepository.save(product);

        // Редиректим на /admin, чтобы при обновлении страницы не было повторной отправки формы
        return "redirect:/admin";
    }
}
