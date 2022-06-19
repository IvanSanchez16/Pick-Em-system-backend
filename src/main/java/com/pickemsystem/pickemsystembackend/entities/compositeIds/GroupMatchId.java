package com.pickemsystem.pickemsystembackend.entities.compositeIds;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupMatchId implements Serializable {

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "match_id", nullable = false)
    private Integer matchId;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMatchId that = (GroupMatchId) o;
        return groupId.equals(that.groupId) && matchId.equals(that.matchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, matchId);
    }
}