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
    public ResponseEntity<?> login(@RequestBody UserCredential userCredential) {
        String token = authService.login(userCredential);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .build();
    }

}
