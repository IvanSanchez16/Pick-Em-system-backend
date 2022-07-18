package com.pickemsystem.pickemsystembackend.repositories;

import com.pickemsystem.pickemsystembackend.entities.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, UUID> {

    @Query("SELECT c FROM ConfirmationToken c WHERE c.token = ?1 AND c.user.id = ?2")
    Optional<ConfirmationToken> findByTokenAndUser(String token, Long userId);

}