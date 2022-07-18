package com.pickemsystem.pickemsystembackend.repositories;

import com.pickemsystem.pickemsystembackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("SELECT (COUNT(u) > 0) FROM User u WHERE UPPER(u.username) = UPPER(?1)")
    boolean existsByUsername(String username);

    @Query("SELECT (COUNT(u) > 0) FROM User u WHERE UPPER(u.email) = UPPER(?1)")
    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.verifiedAt = ?1 WHERE u.id = ?2")
    int verifyUser(LocalDateTime verifiedAt, Long id);

}