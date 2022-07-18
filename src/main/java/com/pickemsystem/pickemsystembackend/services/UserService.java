package com.pickemsystem.pickemsystembackend.services;

import com.pickemsystem.pickemsystembackend.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User save(User user);

    boolean verifyUser(Long userId);
}
