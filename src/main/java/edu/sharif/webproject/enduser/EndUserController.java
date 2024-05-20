package edu.sharif.webproject.enduser;

import edu.sharif.webproject.enduser.api.ApiTokenDto;
import edu.sharif.webproject.enduser.api.ApiTokenRequest;
import edu.sharif.webproject.enduser.api.ApiTokensDto;
import edu.sharif.webproject.enduser.api.DeleteApiTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class EndUserController {

    private final EndUserService endUserService;

    @PostMapping("/api-tokens")
    public ApiTokenDto getApiTokens(@RequestBody ApiTokenRequest apiTokenRequest) {
        return endUserService.buildApiToken(apiTokenRequest);
    }

    @GetMapping("/api-tokens")
    public ApiTokensDto getAllApiTokens() {
        return endUserService.getAllApiTokens();
    }

    @DeleteMapping("/api-tokens")
    public DeleteApiTokenResponse deleteApiToken(@RequestHeader("X-API-Key") String apiKey) {
        return endUserService.deleteApiToken(apiKey);
    }

}
