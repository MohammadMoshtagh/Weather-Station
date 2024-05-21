package edu.sharif.webproject.enduser.entity;

import edu.sharif.webproject.api.entity.ApiTokenEntity;
import edu.sharif.webproject.enduser.entity.dto.EndUserDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "end_user")
@Data
@NoArgsConstructor
public class EndUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private EndUserRoleEnum role;
    @Column(nullable = false)
    private Boolean enable;

    @OneToMany(mappedBy = "endUser", cascade = CascadeType.ALL)
    @Column(name = "api_tokens")
    private List<ApiTokenEntity> apiTokens;

    public EndUserDto toDto() {
        return new EndUserDto(username, role, enable);
    }
}
