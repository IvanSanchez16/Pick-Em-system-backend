package com.pickemsystem.pickemsystembackend.services;

import com.pickemsystem.pickemsystembackend.entities.main_entities.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User save(User user);

    boolean comparePasswords(User user, String newPassword);

    boolean verifyUser(Long userId);

    public String refreshAccessToken(String requestURL, String refreshToken);
}
