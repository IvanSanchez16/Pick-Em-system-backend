package com.pickemsystem.pickemsystembackend.services.matches_services;

import com.pickemsystem.pickemsystembackend.entities.matches_entities.Team;

import java.util.List;

public interface TeamService {

    Team save(Team team, int regionId);

    List<Team> findByRegion(int regionId);
}
