package edu.sharif.webproject.enduser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EndUserRepository extends JpaRepository<EndUserEntity, Long> {
    Optional<EndUserEntity> findEndUserEntityByUsername(String username);
}
