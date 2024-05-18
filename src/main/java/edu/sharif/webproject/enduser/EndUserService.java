package edu.sharif.webproject.enduser;

import edu.sharif.webproject.enduser.EndUserEntity;
import edu.sharif.webproject.enduser.EndUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EndUserService {

    private final EndUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public EndUserService(EndUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public EndUserEntity register(EndUserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public EndUserEntity login(String username, String password) {
        Optional<EndUserEntity> endUser = userRepository.findEndUserEntityByUsername(username);
        if (endUser.isPresent() && passwordEncoder.matches(password, endUser.get().getPassword())) {
            return endUser.get();
        }
        return null;
    }
}
