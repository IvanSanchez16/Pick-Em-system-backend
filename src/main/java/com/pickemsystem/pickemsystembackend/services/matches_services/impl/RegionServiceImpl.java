package com.pickemsystem.pickemsystembackend.services.matches_services.impl;

import com.pickemsystem.pickemsystembackend.entities.main_entities.Sport;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Region;
import com.pickemsystem.pickemsystembackend.exceptions.BadRequestException;
import com.pickemsystem.pickemsystembackend.repositories.main_repositories.SportRepository;
import com.pickemsystem.pickemsystembackend.repositories.match_repositories.RegionRepository;
import com.pickemsystem.pickemsystembackend.services.matches_services.RegionService;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final SportRepository sportRepository;

    @Override
    public Region save(Region region, int sportId) {
        Optional<Sport> optionalSport = sportRepository.findById(sportId);
        if (optionalSport.isPresent())
            region.setSport(optionalSport.get());
        else
            throw new BadRequestException(AppMessages.SPORT_NOT_FOUND);
        return regionRepository.save(region);
    }

    @Override
    public List<Region> findBySportId(int sportId) {
        return regionRepository.findBySport_Id(sportId);
    }
}
