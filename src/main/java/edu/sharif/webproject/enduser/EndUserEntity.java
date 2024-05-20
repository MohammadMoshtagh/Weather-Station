package edu.sharif.webproject.enduser;

import edu.sharif.webproject.enduser.api.ApiTokenEntity;
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

    @OneToMany(mappedBy = "endUser")
    @Column(name = "api_tokens")
    private List<ApiTokenEntity> apiTokens;
}
