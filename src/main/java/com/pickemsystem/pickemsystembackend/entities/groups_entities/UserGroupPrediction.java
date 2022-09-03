package com.pickemsystem.pickemsystembackend.entities.groups_entities;

import com.pickemsystem.pickemsystembackend.entities.groups_entities.composite_ids.UserGroupPredictionId;
import com.pickemsystem.pickemsystembackend.entities.main_entities.User;
import com.pickemsystem.pickemsystembackend.entities.matches_entities.Team;

import javax.persistence.*;

@Entity
@Table(name = "user_group_prediction")
public class UserGroupPrediction {
    @EmbeddedId
    private UserGroupPredictionId userGroupPredictionId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("groupId")
    private Group group;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("userId")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @MapsId("teamId")
    private Team team;

    @Column(name = "position", nullable = false)
    private Short position;

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UserGroupPredictionId getUserGroupPredictionId() {
        return userGroupPredictionId;
    }

    public void setUserGroupPredictionId(UserGroupPredictionId userGroupPredictionId) {
        this.userGroupPredictionId = userGroupPredictionId;
    }

}