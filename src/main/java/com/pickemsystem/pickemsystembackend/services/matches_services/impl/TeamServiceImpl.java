package com.pickemsystem.pickemsystembackend.services.matches_services.impl;

import com.pickemsystem.pickemsystembackend.entities.matches_entities.Region;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Team;
import com.pickemsystem.pickemsystembackend.exceptions.BadRequestException;
import com.pickemsystem.pickemsystembackend.repositories.match_repositories.RegionRepository;
import com.pickemsystem.pickemsystembackend.repositories.match_repositories.TeamRepository;
import com.pickemsystem.pickemsystembackend.services.matches_services.TeamService;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final RegionRepository regionRepository;

    @Override
    public Team save(Team team, int regionId) {
        Optional<Region> optionalRegion = regionRepository.findById(regionId);
        if (optionalRegion.isPresent())
            team.setRegion(optionalRegion.get());
        else
            throw new BadRequestException(AppMessages.REGION_NOT_FOUND);

        return teamRepository.save(team);
    }

    @Override
    public List<Team> findByRegion(int regionId) {
        return teamRepository.findByRegion_Id(regionId);
    }
}
