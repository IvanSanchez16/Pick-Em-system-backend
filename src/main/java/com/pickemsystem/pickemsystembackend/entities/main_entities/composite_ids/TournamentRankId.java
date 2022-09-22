package com.pickemsystem.pickemsystembackend.entities.main_entities.composite_ids;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class TournamentRankId implements Serializable {

    @Column(name = "tournament_id", nullable = false)
    private Long tournamentId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }
}