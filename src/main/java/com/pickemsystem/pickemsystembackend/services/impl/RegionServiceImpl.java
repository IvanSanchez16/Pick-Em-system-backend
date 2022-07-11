package com.pickemsystem.pickemsystembackend.services.impl;

import com.pickemsystem.pickemsystembackend.entities.Region;
import com.pickemsystem.pickemsystembackend.repositories.RegionRepository;
import com.pickemsystem.pickemsystembackend.services.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public Region save(Region region) {
        return regionRepository.save(region);
    }

    @Override
    public List<Region> findAll() {
        return regionRepository.findAll();
    }
}
