package com.pickemsystem.pickemsystembackend.controllers;

import com.pickemsystem.pickemsystembackend.dto.requests.TeamCreateDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.TeamDTO;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Team;
import com.pickemsystem.pickemsystembackend.mappers.TeamMapper;
import com.pickemsystem.pickemsystembackend.services.matches_services.TeamService;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/region/{regionId}")
    public ResponseEntity<ApiResponseDTO> getByRegion(@PathVariable int regionId) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();

        List<TeamDTO> teamDTOS = new ArrayList<>();
        List<Team> teams = teamService.findByRegion(regionId);
        teams.forEach(team -> teamDTOS.add(TeamMapper.mapToDTO(team)));

        apiResponseDTO.setData(teamDTOS);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> save(@RequestBody @Valid TeamCreateDTO teamCreateDTO) {
        Team team = TeamMapper.mapToEntity(teamCreateDTO);
        team = teamService.save(team, teamCreateDTO.getRegionId());

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(AppMessages.TEAM_CREATED, TeamMapper.mapToDTO(team));
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }
}
