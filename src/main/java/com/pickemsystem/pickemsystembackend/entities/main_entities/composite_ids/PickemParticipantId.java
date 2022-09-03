package com.pickemsystem.pickemsystembackend.entities.main_entities.composite_ids;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PickemParticipantId implements Serializable {
    @Column(name = "pickem_id", nullable = false)
    private Long pickemId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        PickemParticipantId that = (PickemParticipantId) o;
        return pickemId.equals(that.pickemId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickemId, userId);
    }
}