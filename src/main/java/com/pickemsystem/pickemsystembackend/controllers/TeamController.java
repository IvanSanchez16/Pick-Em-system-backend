package com.pickemsystem.pickemsystembackend.controllers;

import com.pickemsystem.pickemsystembackend.dto.requests.TeamCreateDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.TeamDTO;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Team;
import com.pickemsystem.pickemsystembackend.images.EntityType;
import com.pickemsystem.pickemsystembackend.mappers.TeamMapper;
import com.pickemsystem.pickemsystembackend.services.matches_services.TeamService;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import com.pickemsystem.pickemsystembackend.images.ImageManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final ImageManager imageManager;

    @Value("${server.images.url}")
    private String imagesUrl;

    @GetMapping("/region/{regionId}")
    public ResponseEntity<ApiResponseDTO> getByRegion(@PathVariable int regionId) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();

        List<TeamDTO> teamDTOS = new ArrayList<>();
        List<Team> teams = teamService.findByRegion(regionId);
        teams.forEach(team -> teamDTOS.add(TeamMapper.mapToDTO(team)));

        apiResponseDTO.setData(teamDTOS);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDTO> save(@ModelAttribute @Valid TeamCreateDTO teamCreateDTO) {
        Team team = TeamMapper.mapToEntity(teamCreateDTO);
        team = teamService.save(team, teamCreateDTO.getRegionId());

        if (teamCreateDTO.getImage() != null)
            imageManager.resizeAndSaveFile(teamCreateDTO.getImage(), EntityType.TEAM, team.getId());

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(AppMessages.TEAM_CREATED, TeamMapper.mapToDTO(team));
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/image/{teamId}",
            produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable int teamId) throws IOException {
        File file = imageManager.getImage(EntityType.TEAM, teamId);

        BufferedImage bufferedImage = ImageIO.read(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", bos);
        return bos.toByteArray();
    }
}
