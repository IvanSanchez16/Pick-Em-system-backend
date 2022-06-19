package com.pickemsystem.pickemsystembackend.entities.compositeIds;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupTeamId implements Serializable {
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupTeamId that = (GroupTeamId) o;
        return groupId.equals(that.groupId) && teamId.equals(that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, teamId);
    }
}