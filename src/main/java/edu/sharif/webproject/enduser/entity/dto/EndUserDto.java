package edu.sharif.webproject.enduser.entity.dto;

import edu.sharif.webproject.enduser.entity.EndUserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EndUserDto {
    private String username;
    private EndUserRoleEnum role;
    private Boolean enable;
}
