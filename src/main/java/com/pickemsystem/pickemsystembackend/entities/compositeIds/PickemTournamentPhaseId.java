package com.pickemsystem.pickemsystembackend.entities.compositeIds;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PickemTournamentPhaseId implements Serializable {
    @Column(name = "pickem_id", nullable = false)
    private Long pickemId;

    @Column(name = "tournament_id", nullable = false)
    private Long tournamentId;

    @Column(name = "phase_type_id", nullable = false)
    private Integer phaseTypeId;

    public Integer getPhaseTypeId() {
        return phaseTypeId;
    }

    public void setPhaseTypeId(Integer phaseTypeId) {
        this.phaseTypeId = phaseTypeId;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Long getPickemId() {
        return pickemId;
    }

    public void setPickemId(Long pickemId) {
        this.pickemId = pickemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PickemTournamentPhaseId that = (PickemTournamentPhaseId) o;
        return pickemId.equals(that.pickemId) && tournamentId.equals(that.tournamentId) && phaseTypeId.equals(that.phaseTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickemId, tournamentId, phaseTypeId);
    }
}