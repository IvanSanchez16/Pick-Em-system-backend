package com.pickemsystem.pickemsystembackend.entities;

import com.pickemsystem.pickemsystembackend.entities.compositeIds.GroupMatchId;

import javax.persistence.*;

@Entity
@Table(name = "group_matches")
public class GroupMatch {
    @EmbeddedId
    private GroupMatchId groupMatchId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("groupId")
    private Group group;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("matchId")
    private Match match;

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public GroupMatchId getGroupMatchId() {
        return groupMatchId;
    }

    public void setGroupMatchId(GroupMatchId groupMatchId) {
        this.groupMatchId = groupMatchId;
    }

}