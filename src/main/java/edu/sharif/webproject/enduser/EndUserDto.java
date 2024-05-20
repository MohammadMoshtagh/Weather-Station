package edu.sharif.webproject.enduser;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EndUserDto {
    private String username;
    private EndUserRoleEnum role;
    private Boolean enable;
}
