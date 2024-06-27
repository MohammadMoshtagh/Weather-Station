package edu.sharif.webproject.security;

import edu.sharif.webproject.enduser.entity.dto.EndUserDto;
import edu.sharif.webproject.security.entity.dto.UserCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public EndUserDto register(@RequestBody UserCredential userCredential) {
        return authService.register(userCredential);
    }

    @PostMapping("/login")
    public ResponseEntity<EndUserDto> login(@RequestBody UserCredential userCredential) {
        return authService.login(userCredential);
    }

}
