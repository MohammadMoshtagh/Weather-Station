package edu.sharif.webproject.enduser;

import edu.sharif.webproject.enduser.api.ApiTokenDto;
import edu.sharif.webproject.enduser.api.ApiTokenRequest;
import edu.sharif.webproject.enduser.api.ApiTokensResponse;
import edu.sharif.webproject.enduser.api.DeleteApiTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class EndUserController {

    private final EndUserService endUserService;

    @PostMapping("/api-tokens")
    public ApiTokenDto getApiToken(@RequestBody ApiTokenRequest apiTokenRequest) {
        return endUserService.buildApiToken(apiTokenRequest);
    }

    @GetMapping("/api-tokens")
    public ApiTokensResponse getAllApiTokens(@RequestParam int pageNum, @RequestParam int pageSize) {
        return endUserService.getAllApiTokens(pageNum, pageSize);
    }

    @DeleteMapping("/api-tokens")
    public DeleteApiTokenResponse deleteApiToken(@RequestHeader("X-API-Key") String apiKey) {
        return endUserService.deleteApiToken(apiKey);
    }

}
