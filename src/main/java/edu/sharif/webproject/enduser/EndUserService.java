package edu.sharif.webproject.enduser;

import edu.sharif.webproject.enduser.api.*;
import edu.sharif.webproject.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EndUserService {

    private final EndUserRepository userRepository;
    private final ApiTokenRepository apiTokenRepository;

    public ApiTokenDto buildApiToken(ApiTokenRequest apiTokenRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<EndUserEntity> endUserOptional = userRepository.findEndUserEntityByUsername(username);
        if (endUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found!");
        }
        EndUserEntity endUser = endUserOptional.get();
        ApiTokenEntity apiTokenEntity = new ApiTokenEntity();
        apiTokenEntity.setEndUser(endUser);
        apiTokenEntity.setName(apiTokenRequest.getName());
        apiTokenEntity.setExpirationDate(apiTokenRequest.getExpirationDate());
        apiTokenEntity.setApiToken(UUID.randomUUID().toString());
        return apiTokenRepository.save(apiTokenEntity).toDto();
    }

    public ApiTokensDto getAllApiTokens() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<EndUserEntity> endUserOptional = userRepository.findEndUserEntityByUsername(username);
        if (endUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found!");
        }
        EndUserEntity endUser = endUserOptional.get();
        Optional<List<ApiTokenEntity>> apiTokensOptional = apiTokenRepository.findApiTokenEntitiesByEndUser(endUser);
        if (apiTokensOptional.isEmpty()) {
            return new ApiTokensDto();
        }

        List<ApiTokenDto> apiTokenDtos = apiTokensOptional.get().stream().map(ApiTokenEntity::toDto).map(apiTokenDto -> {
            apiTokenDto.setApiToken("API ***");
            return apiTokenDto;
        }).toList();
        ApiTokensDto apiTokensDto = new ApiTokensDto();
        apiTokensDto.setApiTokens(apiTokenDtos);
        return apiTokensDto;
    }

    public DeleteApiTokenResponse deleteApiToken(String apiKeyToDelete) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<EndUserEntity> endUserOptional = userRepository.findEndUserEntityByUsername(username);
        if (endUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found!");
        }
        EndUserEntity endUser = endUserOptional.get();
        Optional<List<ApiTokenEntity>> apiTokensOptional = apiTokenRepository.findApiTokenEntitiesByEndUser(endUser);
        if (apiTokensOptional.isEmpty()) {
            return new DeleteApiTokenResponse(false);
        }
        Map<String, ApiTokenEntity> apiTokens = apiTokensOptional.get().stream().collect(Collectors.toMap(ApiTokenEntity::getApiToken, x -> x));
        if (!apiTokens.containsKey(apiKeyToDelete)) {
            return new DeleteApiTokenResponse(false);
        }
        ApiTokenEntity apiTokenEntityToDelete = apiTokens.get(apiKeyToDelete);
        apiTokenRepository.delete(apiTokenEntityToDelete);
        return new DeleteApiTokenResponse(true);
    }
}
