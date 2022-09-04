package com.pickemsystem.pickemsystembackend.entities.knockout_entities.composite_ids;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserKnockoutMatchPredictionId implements Serializable {

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "knockout_match_id", nullable = false)
    private Long knockoutMatchId;

    @Column(name = "pickem_id", nullable = false)
    private Long pickemId;

    public Long getPickemId() {
        return pickemId;
    }

    public void setPickemId(Long pickemId) {
        this.pickemId = pickemId;
    }

    public Long getKnockoutMatchId() {
        return knockoutMatchId;
    }

    public void setKnockoutMatchId(Long knockoutMatchId) {
        this.knockoutMatchId = knockoutMatchId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserKnockoutMatchPredictionId that = (UserKnockoutMatchPredictionId) o;
        return userId.equals(that.userId) && knockoutMatchId.equals(that.knockoutMatchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, knockoutMatchId);
    }
}