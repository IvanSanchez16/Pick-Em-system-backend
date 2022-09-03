package com.pickemsystem.pickemsystembackend.entities.knockout_entities;

import com.pickemsystem.pickemsystembackend.entities.knockout_entities.composite_ids.UserKnockoutMatchPredictionId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_knockout_match_prediction")
public class UserKnockoutMatchPrediction {
    @EmbeddedId
    private UserKnockoutMatchPredictionId userKnockoutMatchPredictionId;

    @Column(name = "prediction", nullable = false)
    private Byte prediction;

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