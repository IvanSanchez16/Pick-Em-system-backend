package com.pickemsystem.pickemsystembackend.entities;

import com.pickemsystem.pickemsystembackend.entities.compositeIds.GroupTeamId;

import javax.persistence.*;

@Entity
@Table(name = "groups_teams")
public class GroupTeam {
    @EmbeddedId
    private GroupTeamId groupTeamId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(nullable = false)
    @MapsId("groupId")
    private Group group;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("teamId")
    private Team team;

    @Column(name = "pool", nullable = false)
    private Short pool;

    public Short getPool() {
        return pool;
    }

    public void setPool(Short pool) {
        this.pool = pool;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public GroupTeamId getGroupTeamId() {
        return groupTeamId;
    }

    public void setGroupTeamId(GroupTeamId groupTeamId) {
        this.groupTeamId = groupTeamId;
    }
}