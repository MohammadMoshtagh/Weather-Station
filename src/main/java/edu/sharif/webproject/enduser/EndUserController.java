package edu.sharif.webproject.enduser;

import edu.sharif.webproject.enduser.EndUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class EndUserController {

    private final EndUserRepository endUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EndUserController(EndUserRepository endUserRepository, PasswordEncoder passwordEncoder) {
        this.endUserRepository = endUserRepository;
        this.passwordEncoder = passwordEncoder;
    }
}
