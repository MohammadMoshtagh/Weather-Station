package edu.sharif.webproject.security;

import edu.sharif.webproject.enduser.EndUserDto;
import edu.sharif.webproject.enduser.EndUserEntity;
import edu.sharif.webproject.enduser.EndUserRepository;
import edu.sharif.webproject.security.exception.UsernameAlreadyInUseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static edu.sharif.webproject.enduser.EndUserRoleEnum.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EndUserRepository endUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public EndUserDto register(UserCredential userCredential) {
        if (checkDuplicateUsername(userCredential.username())) {
            throw new UsernameAlreadyInUseException(
                    "User with username %s already exists!".formatted(userCredential.username()));
        }
        EndUserEntity endUser = buildEndUserEntity(userCredential);

        EndUserEntity endUserEntity = endUserRepository.save(endUser);
        return endUserEntity.toDto();
    }

    public String login(UserCredential userCredential) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userCredential.username(), userCredential.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return jwtService.generateToken(authentication.getName());
    }

    private EndUserEntity buildEndUserEntity(UserCredential userCredential) {
        EndUserEntity endUser = new EndUserEntity();
        endUser.setUsername(userCredential.username());
        endUser.setPassword(passwordEncoder.encode(userCredential.password()));
        endUser.setEnable(false);
        endUser.setRole(USER);

        return endUser;
    }

    private boolean checkDuplicateUsername(String username) {
        Optional<EndUserEntity> endUserOptional = endUserRepository.findEndUserEntityByUsername(username);
        return endUserOptional.isPresent();
    }
}
