package com.pickemsystem.pickemsystembackend.services;

import java.util.Optional;

public interface TokenService<T> {

    void save(T token);

    Optional<T> findByTokenAndUser(String token, Long userId);

    void deleteByUser(Long userId);
}
