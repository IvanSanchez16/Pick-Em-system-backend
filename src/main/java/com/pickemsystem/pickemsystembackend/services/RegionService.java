package com.pickemsystem.pickemsystembackend.services;

import com.pickemsystem.pickemsystembackend.entities.matches_entities.Region;

import java.util.List;

public interface RegionService {

    Region save(Region region, int sportId);

    List<Region> findAll();
}
