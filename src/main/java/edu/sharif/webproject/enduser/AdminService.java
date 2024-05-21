package edu.sharif.webproject.enduser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final EndUserRepository endUserRepository;

    public boolean changeUserState(String username, boolean state) {
        Optional<EndUserEntity> endUserOptional = endUserRepository.findEndUserEntityByUsername(username);
        if (endUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found!");
        }
        EndUserEntity endUser = endUserOptional.get();
        endUser.setEnable(state);
        endUserRepository.save(endUser);
        return true;
    }

    public EndUsersResponse getAllUsers(int pageNum, int pageSize) {
        Page<EndUserEntity> endUsers = endUserRepository.findAll(PageRequest.of(pageNum, pageSize));
        if (endUsers.isEmpty()) {
            return new EndUsersResponse();
        }
        return new EndUsersResponse(endUsers.stream().map(EndUserEntity::toDto).toList());
    }
}
