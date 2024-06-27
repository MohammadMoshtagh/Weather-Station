package edu.sharif.webproject.authentication.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApiTokensResponse {
    @JsonProperty("tokens")
    private List<ApiTokenDto> apiTokensDto;
    @JsonProperty
    private int count;

    public ApiTokensResponse() {
        apiTokensDto = new ArrayList<>();
        count = 0;
    }

    public ApiTokensResponse(List<ApiTokenDto> apiTokensDto) {
        this.apiTokensDto = apiTokensDto;
        this.count = apiTokensDto.size();
    }
}
