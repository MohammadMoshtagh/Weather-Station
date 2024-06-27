package edu.sharif.webproject.authentication.api.entity;

import edu.sharif.webproject.authentication.enduser.entity.EndUserEntity;
import edu.sharif.webproject.authentication.api.entity.dto.ApiTokenDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "api_tokens")
@Data
@NoArgsConstructor
public class ApiTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true, name = "api_token")
    private String apiToken;
    @Column(nullable = false, name = "expiration_date")
    private Date expirationDate;
    @ManyToOne
    @JoinColumn(nullable = false, name = "end_user")
    private EndUserEntity endUser;

    public ApiTokenDto toDto() {
        return new ApiTokenDto(name, apiToken, expirationDate);
    }
}
