package com.pickemsystem.pickemsystembackend.entities.league_entities.composite_ids;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserLeaguePredictionId implements Serializable {

    @Column(name = "tournament_id", nullable = false)
    private Long tournamentId;

    @Column(name = "pickem_id", nullable = false)
    private Long pickemId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPickemId() {
        return pickemId;
    }

    public void setPickemId(Long pickemId) {
        this.pickemId = pickemId;
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
        UserLeaguePredictionId that = (UserLeaguePredictionId) o;
        return tournamentId.equals(that.tournamentId) && pickemId.equals(that.pickemId) && userId.equals(that.userId) && teamId.equals(that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tournamentId, pickemId, userId, teamId);
    }
}