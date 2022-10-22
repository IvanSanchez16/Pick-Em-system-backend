package com.pickemsystem.pickemsystembackend.repositories.match_repositories;

import com.pickemsystem.pickemsystembackend.entities.matches_entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    List<Region> findBySport_Id(Integer id);

}