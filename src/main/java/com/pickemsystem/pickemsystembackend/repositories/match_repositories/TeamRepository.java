package com.pickemsystem.pickemsystembackend.repositories.match_repositories;

import com.pickemsystem.pickemsystembackend.entities.matches_entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByRegion_Id(Integer id);

}
