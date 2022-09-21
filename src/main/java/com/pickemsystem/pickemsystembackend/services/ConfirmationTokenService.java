package com.pickemsystem.pickemsystembackend.services;

import com.pickemsystem.pickemsystembackend.entities.main_entities.ConfirmationToken;
import com.pickemsystem.pickemsystembackend.repositories.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Transactional
    public void save(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> findByTokenAndUser(String token, Long userId){
        return confirmationTokenRepository.findByTokenAndUser(token, userId);
    }

    @Transactional
    public void deleteByUser(Long userId) {
        confirmationTokenRepository.deleteByUser(userId);
    }
}
