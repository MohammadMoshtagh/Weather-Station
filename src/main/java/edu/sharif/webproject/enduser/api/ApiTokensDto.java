package edu.sharif.webproject.enduser.api;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApiTokensDto {
    private List<ApiTokenDto> apiTokensDto;
    private int count;

    public ApiTokensDto() {
        apiTokensDto = new ArrayList<>();
        count = 0;
    }

    public void setApiTokens(List<ApiTokenDto> apiTokensDto) {
        this.apiTokensDto = apiTokensDto;
        this.count = apiTokensDto.size();
    }


}
