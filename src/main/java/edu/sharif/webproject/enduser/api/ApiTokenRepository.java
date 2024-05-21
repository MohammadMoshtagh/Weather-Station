package edu.sharif.webproject.enduser.api;


import edu.sharif.webproject.enduser.EndUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiTokenRepository extends JpaRepository<ApiTokenEntity, Long> {
    Optional<List<ApiTokenEntity>> findApiTokenEntitiesByEndUser(EndUserEntity endUser, Pageable pageable);
    Optional<List<ApiTokenEntity>> findApiTokenEntitiesByEndUser(EndUserEntity endUser);
    Optional<ApiTokenEntity> findApiTokenEntitiesByApiToken(String apiToken);
}
