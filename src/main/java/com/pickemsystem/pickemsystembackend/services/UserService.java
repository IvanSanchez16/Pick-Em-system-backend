package com.pickemsystem.pickemsystembackend.services;

import com.pickemsystem.pickemsystembackend.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    User save(User user);
}
