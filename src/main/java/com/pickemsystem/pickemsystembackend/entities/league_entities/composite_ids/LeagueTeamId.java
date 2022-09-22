package com.pickemsystem.pickemsystembackend.entities.league_entities.composite_ids;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LeagueTeamId implements Serializable {

    @Column(name = "tournament_id", nullable = false)
    private Long tournamentId;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeagueTeamId that = (LeagueTeamId) o;
        return tournamentId.equals(that.tournamentId) && teamId.equals(that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tournamentId, teamId);
    }
}
