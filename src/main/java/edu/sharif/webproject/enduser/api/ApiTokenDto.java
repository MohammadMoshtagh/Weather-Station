package edu.sharif.webproject.enduser.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApiTokenDto {
    @JsonProperty
    private String name;
    @JsonProperty("api_token")
    private String apiToken;
//    @JsonProperty("expire_date")
//    private Date expirationDate;

    public ApiTokenDto(String name, String apiToken, Date expirationDate) {
        this.name = name;
        this.apiToken = apiToken;
//        this.expirationDate = expirationDate;
    }
}
