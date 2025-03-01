package ru.company.positiv.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.company.positiv.dto.RefreshRoleAdminDTO;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;

import java.util.Optional;

@Controller
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {
    private final UserRepositories userRepositories;

    @GetMapping()
    public String owner(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("peopleNotAdmin", userRepositories.findAllByRoleNot("ADMIN"));
        model.addAttribute("peopleNotUser", userRepositories.findAllByRoleNot("USER"));
        return "personalAccount/ownerPanel";
    }

    @PostMapping("/add")
    public String makeAdmin(@ModelAttribute("makeAdminDTO") RefreshRoleAdminDTO refreshRoleAdminDTO) {
        Optional<User> optional = userRepositories.findById(refreshRoleAdminDTO.getId());
        if (optional.isPresent()) {
            User userOne = optional.get();
            userOne.setRole("ADMIN");
            userRepositories.save(userOne);
        }
        return "redirect:/owner";
    }

    @PostMapping("/delete")
    public String deleteAdmin(@ModelAttribute("deleteAdminDTO") RefreshRoleAdminDTO refreshRoleAdminDTO) {
        Optional<User> optional = userRepositories.findById(refreshRoleAdminDTO.getId());
        if (optional.isPresent()) {
            User user = optional.get();
            user.setRole("USER");
            userRepositories.save(user);
        }
        return "redirect:/owner";

    }

}
