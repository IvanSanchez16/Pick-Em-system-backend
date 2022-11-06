package com.pickemsystem.pickemsystembackend.controllers;

import com.pickemsystem.pickemsystembackend.dto.requests.RegionCreateDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.RegionDTO;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Region;
import com.pickemsystem.pickemsystembackend.images.EntityType;
import com.pickemsystem.pickemsystembackend.images.ImageManager;
import com.pickemsystem.pickemsystembackend.mappers.RegionMapper;
import com.pickemsystem.pickemsystembackend.services.matches_services.RegionService;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regions")
public class RegionController {

    private final RegionService regionService;

    private final ImageManager imageManager;

    @GetMapping("/sport/{sportId}")
    public ResponseEntity<ApiResponseDTO> getBySport(@PathVariable int sportId) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();

        List<RegionDTO> regionDTOS = new ArrayList<>();
        List<Region> regions = regionService.findBySportId(sportId);
        regions.forEach(region -> regionDTOS.add(RegionMapper.mapToDTO(region)));

        apiResponseDTO.setData(regionDTOS);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDTO> save(@ModelAttribute @Valid RegionCreateDTO regionCreateDTO){
        Region region = RegionMapper.mapToEntity(regionCreateDTO);

        region = regionService.save(region, regionCreateDTO.getSportId());

        if (regionCreateDTO.getImage() != null)
            imageManager.resizeAndSaveFile(regionCreateDTO.getImage(), EntityType.REGION, region.getId());

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(AppMessages.REGION_CREATED, RegionMapper.mapToDTO(region));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/image/{regionId}",
            produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable int regionId) throws IOException {
        File file = imageManager.getImage(EntityType.REGION, regionId);

        BufferedImage bufferedImage = ImageIO.read(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", bos);
        return bos.toByteArray();
    }

}
