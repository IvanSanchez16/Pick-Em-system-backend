package com.pickemsystem.pickemsystembackend.services.impl;

import com.pickemsystem.pickemsystembackend.entities.main_entities.PasswordResetToken;
import com.pickemsystem.pickemsystembackend.repositories.PasswordResetTokenRepository;
import com.pickemsystem.pickemsystembackend.services.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PasswordResetTokenServiceImpl implements TokenService<PasswordResetToken> {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    @Transactional
    public void save(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public Optional<PasswordResetToken> findByTokenAndUser(String token, Long userId) {
        return passwordResetTokenRepository.findByTokenAndUser(token, userId);
    }

    @Override
    @Transactional
    public void deleteByUser(Long userId) {
        passwordResetTokenRepository.deleteByUser(userId);
    }
}
