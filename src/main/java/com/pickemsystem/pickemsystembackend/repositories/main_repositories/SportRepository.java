package com.pickemsystem.pickemsystembackend.repositories.main_repositories;

import com.pickemsystem.pickemsystembackend.entities.main_entities.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Integer> {
}