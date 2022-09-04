package com.pickemsystem.pickemsystembackend.entities.knockout_entities;

import com.pickemsystem.pickemsystembackend.entities.knockout_entities.composite_ids.UserKnockoutMatchPredictionId;
import com.pickemsystem.pickemsystembackend.entities.main_entities.Pickem;
import com.pickemsystem.pickemsystembackend.entities.main_entities.User;

import javax.persistence.*;

@Entity
@Table(name = "user_knockout_match_prediction")
public class UserKnockoutMatchPrediction {
    @EmbeddedId
    private UserKnockoutMatchPredictionId userKnockoutMatchPredictionId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("userId")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("knockoutMatchId")
    private KnockoutMatch knockoutMatch;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("pickemId")
    private Pickem pickemId;

    @Column(name = "prediction", nullable = false)
    private Byte prediction;

    public Pickem getPickemId() {
        return pickemId;
    }

    public void setPickemId(Pickem pickemId) {
        this.pickemId = pickemId;
    }

    public KnockoutMatch getKnockoutMatch() {
        return knockoutMatch;
    }

    public void setKnockoutMatch(KnockoutMatch knockoutMatch) {
        this.knockoutMatch = knockoutMatch;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Byte getPrediction() {
        return prediction;
    }

    public void setPrediction(Byte prediction) {
        this.prediction = prediction;
    }

    public UserKnockoutMatchPredictionId getUserKnockoutMatchPrediction() {
        return userKnockoutMatchPredictionId;
    }

    public void setUserKnockoutMatchPrediction(UserKnockoutMatchPredictionId userKnockoutMatchPredictionId) {
        this.userKnockoutMatchPredictionId = userKnockoutMatchPredictionId;
    }

}