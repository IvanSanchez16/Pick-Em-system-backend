package com.pickemsystem.pickemsystembackend.entities.main_entities;

import com.pickemsystem.pickemsystembackend.entities.main_entities.composite_ids.PickemParticipantId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ban_list_pickem")
public class PickemBanList {

    @EmbeddedId
    private PickemParticipantId pickemParticipantId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("pickemId")
    private Pickem pickem;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("userId")
    private User user;

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

    public PickemParticipantId getPickemParticipantId() {
        return pickemParticipantId;
    }

    public void setPickemParticipantId(PickemParticipantId pickemParticipantId) {
        this.pickemParticipantId = pickemParticipantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PickemBanList that = (PickemBanList) o;
        return pickemParticipantId.equals(that.pickemParticipantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickemParticipantId);
    }
}