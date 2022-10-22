package com.pickemsystem.pickemsystembackend.mappers;

import com.pickemsystem.pickemsystembackend.dto.requests.TeamCreateDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.TeamDTO;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Team;

public class TeamMapper {

    public static Team mapToEntity(TeamCreateDTO teamCreateDTO) {
        Team team = new Team();

        team.setName(teamCreateDTO.getName());
        if (teamCreateDTO.getImageURL() != null)
            team.setImageUrl(teamCreateDTO.getImageURL());
        else
            team.setImageUrl("");

        return team;
    }

    public static TeamDTO mapToDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();

        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setImageUrl(team.getImageUrl());

        return teamDTO;
    }
}