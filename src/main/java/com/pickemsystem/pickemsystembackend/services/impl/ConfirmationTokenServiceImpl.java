package com.pickemsystem.pickemsystembackend.services.impl;

import com.pickemsystem.pickemsystembackend.entities.main_entities.ConfirmationToken;
import com.pickemsystem.pickemsystembackend.repositories.ConfirmationTokenRepository;
import com.pickemsystem.pickemsystembackend.services.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements TokenService<ConfirmationToken> {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Transactional
    @Override
    public void save(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> findByTokenAndUser(String token, Long userId){
        return confirmationTokenRepository.findByTokenAndUser(token, userId);
    }

    @Override
    @Transactional
    public void deleteByUser(Long userId) {
        confirmationTokenRepository.deleteByUser(userId);
    }
}
