package edu.sharif.webproject.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

//    private final AuthService service;

    @GetMapping("/register")
    public String showRegistrationForm(){
        // create model object to store form data
        return "register";
    }

}
