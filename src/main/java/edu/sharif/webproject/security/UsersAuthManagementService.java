package edu.sharif.webproject.security;

import edu.sharif.webproject.enduser.entity.EndUserEntity;
import edu.sharif.webproject.enduser.entity.EndUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersAuthManagementService implements UserDetailsService {

    private final EndUserRepository endUserRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<EndUserEntity> endUserOptional = endUserRepository.findEndUserEntityByUsername(username);
        if (endUserOptional.isPresent()) {
            EndUserEntity endUser = endUserOptional.get();
            return User.withUsername(username)
                    .password(endUser.getPassword())
                    .roles(endUser.getRole().name())
                    .disabled(!endUser.getEnable())
                    .build();
        } else {
            throw new UsernameNotFoundException("Could not find user with username " + username);
        }
    }

    @Transactional
    public EndUserEntity save(EndUserEntity user){
        EndUserEntity registeredUser = new EndUserEntity();
        registeredUser.setUsername(user.getUsername());
        registeredUser.setPassword(passwordEncoder.encode(user.getPassword()));
        registeredUser.setEnable(user.getEnable());
        registeredUser.setRole(user.getRole());

        return endUserRepository.save(registeredUser);
    }

}
