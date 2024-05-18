package edu.sharif.webproject.enduser;

import edu.sharif.webproject.enduser.EndUserEntity;
import edu.sharif.webproject.enduser.EndUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersAuthManagementService implements UserDetailsService {

    private final EndUserRepository endUserRepository;

    @Autowired
    public UsersAuthManagementService(EndUserRepository endUserRepository) {
        this.endUserRepository = endUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<EndUserEntity> endUserOptional = endUserRepository.findEndUserEntityByUsername(username);
        if (endUserOptional.isPresent()) {
            EndUserEntity endUser = endUserOptional.get();
            return User.withUsername(username)
                    .password(endUser.getPassword())
                    .roles(endUser.getRole())
                    .build();
        } else {
            throw new UsernameNotFoundException("Could not find user with username " + username);
        }

    }

}
