package ua.foxminded.vasilmartsyniuk.car_rest_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}