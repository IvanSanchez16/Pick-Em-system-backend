package com.pickemsystem.pickemsystembackend.repositories;

import com.pickemsystem.pickemsystembackend.entities.matches_entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}