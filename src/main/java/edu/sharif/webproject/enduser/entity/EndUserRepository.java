package edu.sharif.webproject.enduser.entity;

import edu.sharif.webproject.enduser.entity.EndUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EndUserRepository extends JpaRepository<EndUserEntity, Long> {
    Optional<EndUserEntity> findEndUserEntityByUsername(String username);
}
