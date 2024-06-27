package edu.sharif.webproject.security;

import edu.sharif.webproject.enduser.EndUserService;
import edu.sharif.webproject.enduser.entity.dto.EndUserDto;
import edu.sharif.webproject.enduser.entity.EndUserEntity;
import edu.sharif.webproject.enduser.entity.EndUserRepository;
import edu.sharif.webproject.security.entity.dto.UserCredential;
import edu.sharif.webproject.security.exception.UsernameAlreadyInUseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static edu.sharif.webproject.enduser.entity.EndUserRoleEnum.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EndUserService endUserService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public EndUserDto register(UserCredential userCredential) {
        if (endUserService.checkDuplicateUsername(userCredential.username())) {
            throw new UsernameAlreadyInUseException(
                    "User with username %s already exists!".formatted(userCredential.username()));
        }
        EndUserEntity endUser = buildEndUserEntity(userCredential);

        EndUserEntity endUserEntity = endUserService.saveEndUserEntity(endUser);
        return endUserEntity.toDto();
    }

    public ResponseEntity<EndUserDto> login(UserCredential userCredential) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userCredential.username(), userCredential.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication.getName());
        EndUserDto endUserDto = endUserService.getEndUserEntity().toDto();
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .body(endUserDto);
    }

    private EndUserEntity buildEndUserEntity(UserCredential userCredential) {
        EndUserEntity endUser = new EndUserEntity();
        endUser.setUsername(userCredential.username());
        endUser.setPassword(passwordEncoder.encode(userCredential.password()));
        endUser.setEnable(false);
        endUser.setRole(USER);

        return endUser;
    }

}
