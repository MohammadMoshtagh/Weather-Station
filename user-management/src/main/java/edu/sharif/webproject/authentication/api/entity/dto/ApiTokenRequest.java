package edu.sharif.webproject.authentication.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class ApiTokenRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("expire_date")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    private Date expirationDate;

    public ApiTokenRequest(String name, Date expirationDate) {
        this.name = name;
        this.expirationDate = expirationDate;
    }
}
