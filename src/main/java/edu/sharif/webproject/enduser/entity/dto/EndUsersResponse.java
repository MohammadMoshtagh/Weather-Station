package edu.sharif.webproject.enduser.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.sharif.webproject.enduser.entity.dto.EndUserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EndUsersResponse {
    @JsonProperty("users")
    private List<EndUserDto> endUsersDto;
    @JsonProperty
    private int count;

    public EndUsersResponse() {
        endUsersDto = new ArrayList<>();
        count = 0;
    }

    public EndUsersResponse(List<EndUserDto> apiTokensDto) {
        this.endUsersDto = apiTokensDto;
        this.count = apiTokensDto.size();
    }
}
