package com.pickemsystem.pickemsystembackend.entities.main_entities;

import com.pickemsystem.pickemsystembackend.entities.main_entities.composite_ids.PickemTournamentPhaseId;

import javax.persistence.*;

@Entity
@Table(name = "pickem_tournament_phase")
public class PickemTournamentPhase {
    @EmbeddedId
    private PickemTournamentPhaseId pickemTournamentPhaseId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("tournamentId")
    private Tournament tournament;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("phaseTypeId")
    private TournamentPhaseType tournamentPhaseType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("pickemId")
    private Pickem pickem;

    public Pickem getPickem() {
        return pickem;
    }

    public void setPickem(Pickem pickem) {
        this.pickem = pickem;
    }

    public TournamentPhaseType getTournamentPhaseType() {
        return tournamentPhaseType;
    }

    public void setTournamentPhaseType(TournamentPhaseType tournamentPhaseType) {
        this.tournamentPhaseType = tournamentPhaseType;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public PickemTournamentPhaseId getPickemTournamentPhaseId() {
        return pickemTournamentPhaseId;
    }

    public void setPickemTournamentPhaseId(PickemTournamentPhaseId pickemTournamentPhaseId) {
        this.pickemTournamentPhaseId = pickemTournamentPhaseId;
    }
}