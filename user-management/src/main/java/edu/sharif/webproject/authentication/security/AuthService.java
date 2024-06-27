package edu.sharif.webproject.authentication.security;

import static edu.sharif.webproject.authentication.enduser.entity.EndUserRoleEnum.USER;

import edu.sharif.webproject.authentication.enduser.EndUserService;
import edu.sharif.webproject.authentication.enduser.entity.dto.EndUserDto;
import edu.sharif.webproject.authentication.enduser.entity.EndUserEntity;
import edu.sharif.webproject.authentication.enduser.entity.dto.ExternalUserDto;
import edu.sharif.webproject.authentication.security.entity.dto.UserCredential;
import edu.sharif.webproject.authentication.security.exception.UsernameAlreadyInUseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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

    public ResponseEntity<ExternalUserDto> authCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.ok().body(null);
        } else {
            User authenticatedUser = (User) authentication.getPrincipal();
            ExternalUserDto externalUserDto = new ExternalUserDto(authenticatedUser.getUsername());
            return ResponseEntity.ok().body(externalUserDto);
        }
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
