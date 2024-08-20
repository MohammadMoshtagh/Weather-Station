package edu.sharif.webproject.authentication.security;

import edu.sharif.webproject.authentication.enduser.entity.dto.EndUserDto;
import edu.sharif.webproject.authentication.security.entity.dto.UserCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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

    @GetMapping("/auth-check")
    public ResponseEntity<?> authCheck() {
        return authService.authCheck();
    }

}
