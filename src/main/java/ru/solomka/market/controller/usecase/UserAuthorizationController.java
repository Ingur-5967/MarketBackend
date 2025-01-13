package ru.solomka.market.controller.usecase;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/secure")
public class UserAuthorizationController {

    @GetMapping ("/signin")
    public String authorization() {
        return "login";
    }

    @GetMapping ("/signup")
    public String registration() {
        return "registration";
    }
}