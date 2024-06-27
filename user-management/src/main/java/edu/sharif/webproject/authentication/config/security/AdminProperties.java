package edu.sharif.webproject.authentication.config.security;

import edu.sharif.webproject.authentication.enduser.entity.EndUserEntity;
import edu.sharif.webproject.authentication.enduser.entity.EndUserRoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "admin")
@Getter
@Setter
public class AdminProperties {
    private String username;
    private String password;
    private String roles;

    public EndUserEntity getEndUser() {
        EndUserEntity endUser = new EndUserEntity();
        endUser.setUsername(username);
        endUser.setPassword(password);
        endUser.setRole(EndUserRoleEnum.valueOf(roles));
        endUser.setEnable(true);
        return endUser;
    }
}