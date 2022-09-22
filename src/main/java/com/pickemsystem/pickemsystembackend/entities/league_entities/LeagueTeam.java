package com.pickemsystem.pickemsystembackend.entities.league_entities;

import com.pickemsystem.pickemsystembackend.entities.league_entities.composite_ids.LeagueTeamId;
import com.pickemsystem.pickemsystembackend.entities.main_entities.Tournament;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Team;

import javax.persistence.*;

@Entity
@Table(name = "league_teams")
public class LeagueTeam {

    @EmbeddedId
    private LeagueTeamId leagueTeamId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("tournamentId")
    private Tournament tournament;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("teamId")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public LeagueTeamId getLeagueTeamId() {
        return leagueTeamId;
    }

    public void setLeagueTeamId(LeagueTeamId leagueTeamId) {
        this.leagueTeamId = leagueTeamId;
    }

}