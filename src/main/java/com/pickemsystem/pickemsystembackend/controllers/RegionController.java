package com.pickemsystem.pickemsystembackend.controllers;

import com.pickemsystem.pickemsystembackend.dto.requests.RegionCreateDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.RegionDTO;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Region;
import com.pickemsystem.pickemsystembackend.mappers.RegionMapper;
import com.pickemsystem.pickemsystembackend.services.matches_services.RegionService;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regions")
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/sport/{sportId}")
    public ResponseEntity<ApiResponseDTO> getBySport(@PathVariable int sportId) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();

        List<RegionDTO> regionDTOS = new ArrayList<>();
        List<Region> regions = regionService.findBySportId(sportId);
        regions.forEach(region -> regionDTOS.add(RegionMapper.mapToDTO(region)));

        apiResponseDTO.setData(regionDTOS);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> save(@RequestBody @Valid RegionCreateDTO regionCreateDTO){
        Region region = RegionMapper.mapToEntity(regionCreateDTO);

        region = regionService.save(region, regionCreateDTO.getSportId());

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(AppMessages.REGION_CREATED, RegionMapper.mapToDTO(region));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

}
