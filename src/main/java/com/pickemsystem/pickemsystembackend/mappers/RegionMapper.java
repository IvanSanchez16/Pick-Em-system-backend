package com.pickemsystem.pickemsystembackend.mappers;

import com.pickemsystem.pickemsystembackend.dto.requests.RegionCreateDTO;
import com.pickemsystem.pickemsystembackend.entities.Region;

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
}
