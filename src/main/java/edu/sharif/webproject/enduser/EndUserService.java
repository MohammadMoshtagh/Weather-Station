package edu.sharif.webproject.enduser;

import edu.sharif.webproject.api.entity.ApiTokenEntity;
import edu.sharif.webproject.api.ApiTokenService;
import edu.sharif.webproject.api.entity.dto.ApiTokenDto;
import edu.sharif.webproject.api.entity.dto.ApiTokenRequest;
import edu.sharif.webproject.api.entity.dto.ApiTokensResponse;
import edu.sharif.webproject.api.entity.dto.DeleteApiTokenResponse;
import edu.sharif.webproject.enduser.entity.EndUserEntity;
import edu.sharif.webproject.enduser.entity.EndUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EndUserService {

    private final EndUserRepository userRepository;
    private final ApiTokenService apiTokenService;

    public ApiTokenDto buildApiToken(ApiTokenRequest apiTokenRequest) {
        EndUserEntity endUser = getEndUserEntity();
        ApiTokenEntity apiToken = apiTokenService.buildApiToken(
                endUser,
                apiTokenRequest.getName(),
                apiTokenRequest.getExpirationDate());
        apiToken = apiTokenService.saveApiToken(apiToken);
        return apiToken.toDto();
    }

    public ApiTokensResponse getAllApiTokens(@RequestParam int pageNum, @RequestParam int pageSize) {
        EndUserEntity endUser = getEndUserEntity();
        List<ApiTokenEntity> apiTokens = apiTokenService.getApiTokensByEndUser(endUser, pageNum, pageSize);
        List<ApiTokenDto> apiTokenDtos = apiTokens.stream().map(ApiTokenEntity::toDto)
                .peek(apiTokenDto -> apiTokenDto.setApiToken("API ***")).toList();
        return new ApiTokensResponse(apiTokenDtos);
    }

    public DeleteApiTokenResponse deleteApiToken(String apiKeyToDelete) {
        EndUserEntity endUser = getEndUserEntity();
        List<ApiTokenEntity> apiTokens = apiTokenService.getApiTokensByEndUser(endUser);
        Map<String, ApiTokenEntity> apiTokensMap = apiTokens.stream()
                .collect(Collectors.toMap(ApiTokenEntity::getApiToken, apiToken -> apiToken));
        if (!apiTokensMap.containsKey(apiKeyToDelete)) {
            return new DeleteApiTokenResponse(false);
        }
        ApiTokenEntity apiTokenEntityToDelete = apiTokensMap.get(apiKeyToDelete);
        apiTokenService.deleteApiToken(apiTokenEntityToDelete);
        return new DeleteApiTokenResponse(true);
    }

    public EndUserEntity getEndUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<EndUserEntity> endUserOptional = userRepository.findEndUserEntityByUsername(username);
        if (endUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found!");
        }
        return endUserOptional.get();
    }

    public EndUserEntity saveEndUserEntity(EndUserEntity endUser) {
        return userRepository.save(endUser);
    }

    public boolean checkDuplicateUsername(String username) {
        Optional<EndUserEntity> endUserOptional = userRepository.findEndUserEntityByUsername(username);
        return endUserOptional.isPresent();
    }
}
