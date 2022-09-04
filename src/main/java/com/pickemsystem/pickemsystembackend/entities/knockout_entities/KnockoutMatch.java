package com.pickemsystem.pickemsystembackend.entities.knockout_entities;

import com.pickemsystem.pickemsystembackend.entities.main_entities.Tournament;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Match;

import javax.persistence.*;

@Entity
@Table(name = "knockout_matches")
public class KnockoutMatch {
    @Id
    @Column(name = "knockout_match_id", nullable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "match_id", nullable = false, unique = true)
    private Match match;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "match_for_winner")
    private KnockoutMatch knockoutMatchForWinner;

    public KnockoutMatch getKnockoutMatchForWinner() {
        return knockoutMatchForWinner;
    }

    public void setKnockoutMatchForWinner(KnockoutMatch knockoutMatchForWinner) {
        this.knockoutMatchForWinner = knockoutMatchForWinner;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}