package edu.sharif.webproject.security;

import static edu.sharif.webproject.enduser.EndUserRoleEnum.USER;

import edu.sharif.webproject.enduser.EndUserEntity;
import edu.sharif.webproject.enduser.EndUserRepository;
import edu.sharif.webproject.security.exception.InvalidApiKeyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final EndUserRepository endUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Autowired
    public AuthService(EndUserRepository endUserRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.endUserRepository = endUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String register(UserCredential userCredential) throws InvalidApiKeyRequest {
        if (checkDuplicateUsername(userCredential.username())) {
            throw new InvalidApiKeyRequest();
        }
        EndUserEntity endUser = new EndUserEntity();
        endUser.setUsername(userCredential.username());
        endUser.setPassword(passwordEncoder.encode(userCredential.password()));
        endUser.setEnable(false);
        endUser.setRole(USER);

        endUserRepository.save(endUser);

        return login(userCredential);
    }

    public String login(UserCredential userCredential) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userCredential.username(),userCredential.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return jwtService.generateToken(authentication.getName());
    }

    private boolean checkDuplicateUsername(String username) {
        Optional<EndUserEntity> endUserOptional = endUserRepository.findEndUserEntityByUsername(username);
        return endUserOptional.isPresent();
    }
}
