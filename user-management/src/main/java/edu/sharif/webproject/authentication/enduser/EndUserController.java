package edu.sharif.webproject.authentication.enduser;

import edu.sharif.webproject.authentication.api.entity.dto.ApiTokenDto;
import edu.sharif.webproject.authentication.api.entity.dto.ApiTokenRequest;
import edu.sharif.webproject.authentication.api.entity.dto.ApiTokensResponse;
import edu.sharif.webproject.authentication.api.entity.dto.DeleteApiTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
