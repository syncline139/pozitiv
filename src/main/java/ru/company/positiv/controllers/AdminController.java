package ru.company.positiv.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.company.positiv.models.Order;
import ru.company.positiv.models.Product;
import ru.company.positiv.repositories.OrderRepositories;
import ru.company.positiv.repositories.ProductRepositories;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductRepositories productRepository;
    private final OrderRepositories orderRepository;

    @GetMapping
    public String showAdminPage(Model model) {
        model.addAttribute("product", new Product());

        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);

        return "/store/adminPage";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            List<Order> orders = orderRepository.findAll();
            model.addAttribute("orders", orders);

            return "/store/adminPage";
        }

        productRepository.save(product);

        return "redirect:/admin";
    }

    @PostMapping("/updateStatus")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId,
                                    @RequestParam("status") String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ не найден: id=" + orderId));

        order.setStatus(status);
        orderRepository.save(order);

        return "redirect:/admin";
    }
}
