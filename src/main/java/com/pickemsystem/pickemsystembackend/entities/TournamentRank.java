package com.pickemsystem.pickemsystembackend.entities;

import com.pickemsystem.pickemsystembackend.entities.compositeIds.TournamentRankId;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tournament_ranks")
public class TournamentRank {

    @EmbeddedId
    private TournamentRankId tournamentRankId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("tournamentId")
    private Tournament tournament;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("userId")
    private User userId;

    @Column(name = "vote", nullable = false)
    private Boolean vote = false;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Boolean getVote() {
        return vote;
    }

    public void setVote(Boolean vote) {
        this.vote = vote;
    }

    public TournamentRankId getTournamentRankId() {
        return tournamentRankId;
    }

    public void setTournamentRankId(TournamentRankId tournamentRankId) {
        this.tournamentRankId = tournamentRankId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TournamentRank that = (TournamentRank) o;
        return tournamentRankId != null && Objects.equals(tournamentRankId, that.tournamentRankId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tournamentRankId);
    }
}