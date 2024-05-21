package edu.sharif.webproject.enduser.api;

import edu.sharif.webproject.enduser.EndUserEntity;
import edu.sharif.webproject.security.exception.InvalidApiTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ApiTokenService {

    private final ApiTokenRepository apiTokenRepository;

    public ApiTokenEntity buildApiToken(EndUserEntity endUser, String name, Date expirationDate) {
        ApiTokenEntity apiTokenEntity = new ApiTokenEntity();
        apiTokenEntity.setEndUser(endUser);
        apiTokenEntity.setName(name);
        apiTokenEntity.setExpirationDate(expirationDate);
        apiTokenEntity.setApiToken(UUID.randomUUID().toString());

        return apiTokenEntity;
    }

    public ApiTokenEntity saveApiToken(ApiTokenEntity apiToken) {
        return apiTokenRepository.save(apiToken);
    }

    public List<ApiTokenEntity> getApiTokensByEndUser(EndUserEntity endUser) {
        Optional<List<ApiTokenEntity>> apiTokensOptional = apiTokenRepository.findApiTokenEntitiesByEndUser(endUser);
        return apiTokensOptional.orElseGet(ArrayList::new);
    }

    public ApiTokenEntity getApiTokenEntityByApiToken(String apiToken) {
        Optional<ApiTokenEntity> apiTokenOptional = apiTokenRepository.findApiTokenEntitiesByApiToken(apiToken);
        if (apiTokenOptional.isEmpty()) {
            throw new InvalidApiTokenException("API token not found!");
        }
        return apiTokenOptional.get();
    }

    public void deleteApiToken(ApiTokenEntity apiToken) {
        apiTokenRepository.delete(apiToken);
    }

}
