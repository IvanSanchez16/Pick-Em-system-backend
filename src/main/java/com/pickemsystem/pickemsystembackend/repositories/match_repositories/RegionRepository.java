package com.pickemsystem.pickemsystembackend.repositories.match_repositories;

import com.pickemsystem.pickemsystembackend.entities.matches_entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}