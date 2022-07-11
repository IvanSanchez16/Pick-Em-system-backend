package com.pickemsystem.pickemsystembackend.repositories;

import com.pickemsystem.pickemsystembackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u INNER JOIN FETCH u.roles WHERE u.username = ?1")
    Optional<User> findByUsername(String username);


}