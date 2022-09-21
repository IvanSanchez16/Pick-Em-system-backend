package com.pickemsystem.pickemsystembackend.services;

import com.pickemsystem.pickemsystembackend.entities.main_entities.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenService {

    void save(PasswordResetToken passwordResetToken);

    Optional<PasswordResetToken> findByTokenAndUser(String token, Long userId);

    void deleteByUser(Long userId);
}
