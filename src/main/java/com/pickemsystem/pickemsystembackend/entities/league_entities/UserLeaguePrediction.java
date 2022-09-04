package com.pickemsystem.pickemsystembackend.entities.league_entities;

import com.pickemsystem.pickemsystembackend.entities.league_entities.composite_ids.UserLeaguePredictionId;
import com.pickemsystem.pickemsystembackend.entities.main_entities.Pickem;
import com.pickemsystem.pickemsystembackend.entities.main_entities.Tournament;
import com.pickemsystem.pickemsystembackend.entities.main_entities.User;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Team;

import javax.persistence.*;

@Entity
@Table(name = "user_league_prediction")
public class UserLeaguePrediction {
    @EmbeddedId
    private UserLeaguePredictionId userLeaguePredictionId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("tournamentId")
    private Tournament tournament;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("pickemId")
    private Pickem pickem;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("userId")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("teamId")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pickem getPickem() {
        return pickem;
    }

    public void setPickem(Pickem pickem) {
        this.pickem = pickem;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public UserLeaguePredictionId getUserLeaguePredictionId() {
        return userLeaguePredictionId;
    }

    public void setUserLeaguePredictionId(UserLeaguePredictionId userLeaguePredictionId) {
        this.userLeaguePredictionId = userLeaguePredictionId;
    }

}