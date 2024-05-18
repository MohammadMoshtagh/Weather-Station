package edu.sharif.webproject.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

//    private final AuthService service;

    @PostMapping("/register")
    public void register() {
        System.out.println("Salam");
    }

}
