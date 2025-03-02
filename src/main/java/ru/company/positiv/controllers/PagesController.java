package ru.company.positiv.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
@RequiredArgsConstructor
public class PagesController {


    // sliding-solutions
    @GetMapping("/sliding-solutions")
    public String slidingSolutions() {
        return "pages/sliding-solutions";
    }

    @GetMapping("/offices")
    public String contacts() {
        return "pages/offices";
    }
}
