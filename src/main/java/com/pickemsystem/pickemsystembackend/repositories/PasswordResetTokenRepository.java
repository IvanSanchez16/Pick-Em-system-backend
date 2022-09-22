package com.pickemsystem.pickemsystembackend.repositories;

import com.pickemsystem.pickemsystembackend.entities.main_entities.PasswordResetToken;
import com.pickemsystem.pickemsystembackend.entities.main_entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {

    @Query("select p from PasswordResetToken p where p.token = ?1 and p.user.id = ?2")
    Optional<PasswordResetToken> findByTokenAndUser(String token, Long id);

    @Modifying
    @Query("delete from PasswordResetToken p where p.user.id = ?1")
    void deleteByUser(Long userId);
}