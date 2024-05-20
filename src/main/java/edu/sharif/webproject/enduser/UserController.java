package edu.sharif.webproject.enduser;

import edu.sharif.webproject.enduser.api.ApiTokenDto;
import edu.sharif.webproject.enduser.api.ApiTokenRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @PostMapping("/api")
    public ApiTokenDto getApiToken(@RequestBody ApiTokenRequest apiTokenRequest) {
        System.out.println("My API is working!");
        return new ApiTokenDto(apiTokenRequest.getName(), "random", apiTokenRequest.getExpirationDate());
    }
}
