package com.pickemsystem.pickemsystembackend.entities.compositeIds;

import javax.persistence.*;
import java.io.Serializable;

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

}