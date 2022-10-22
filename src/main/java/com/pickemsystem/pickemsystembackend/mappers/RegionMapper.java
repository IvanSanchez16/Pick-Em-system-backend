package com.pickemsystem.pickemsystembackend.mappers;

import com.pickemsystem.pickemsystembackend.dto.requests.RegionCreateDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.RegionDTO;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Region;

public class RegionMapper {

    public static Region mapToEntity(RegionCreateDTO regionCreateDTO){
        Region region = new Region();

        region.setName(regionCreateDTO.getName());
        if (regionCreateDTO.getImageURL() != null)
            region.setImageUrl(regionCreateDTO.getImageURL());
        else
            region.setImageUrl("");

        return region;
    }

    public static RegionDTO mapToDTO(Region region) {
        RegionDTO regionDTO = new RegionDTO();

        regionDTO.setId(region.getId());
        regionDTO.setName(region.getName());
        regionDTO.setImageUrl(region.getImageUrl());

        return regionDTO;
    }
}
