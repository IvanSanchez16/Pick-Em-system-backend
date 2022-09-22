package com.pickemsystem.pickemsystembackend.entities.groups_entities;

import com.pickemsystem.pickemsystembackend.entities.main_entities.Tournament;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @Column(name = "char_name", nullable = false)
    private Character charName;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<GroupTeam> teams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<GroupMatch> matches = new LinkedHashSet<>();

    public Set<GroupMatch> getMatches() {
        return matches;
    }

    public void setMatches(Set<GroupMatch> matches) {
        this.matches = matches;
    }

    public Set<GroupTeam> getTeams() {
        return teams;
    }

    public void setTeams(Set<GroupTeam> teams) {
        this.teams = teams;
    }

    public Character getCharName() {
        return charName;
    }

    public void setCharName(Character charName) {
        this.charName = charName;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}