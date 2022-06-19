package com.pickemsystem.pickemsystembackend.entities;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "match_results")
@EqualsAndHashCode
public class MatchResult {

    @Id
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false, orphanRemoval = true)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @Column(name = "first_team_score", nullable = false)
    private Integer firstTeamScore;

    @Column(name = "second_team_score", nullable = false)
    private Integer secondTeamScore;

    public Integer getSecondTeamScore() {
        return secondTeamScore;
    }

    public void setSecondTeamScore(Integer secondTeamScore) {
        this.secondTeamScore = secondTeamScore;
    }

    public Integer getFirstTeamScore() {
        return firstTeamScore;
    }

    public void setFirstTeamScore(Integer firstTeamScore) {
        this.firstTeamScore = firstTeamScore;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}