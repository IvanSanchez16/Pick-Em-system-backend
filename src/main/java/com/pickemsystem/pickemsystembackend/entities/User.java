package com.pickemsystem.pickemsystembackend.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 40)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 115)
    private String email;

    @Lob
    @Column(name = "password", nullable = false)
    private String password;

    @Lob
    @Column(name = "token")
    private String token;

    @OneToMany(mappedBy = "createdBy", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Tournament> tournaments = new ArrayList<>();

    @OneToMany(mappedBy = "created_by", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Pickem> pickems = new LinkedHashSet<>();

    public String getToken() {
        return token;
    }

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "image_url", nullable = false, length = 120)
    private String imageUrl;

    @PrePersist
    private void saveCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Pickem> getPickems() {
        return pickems;
    }

    public void setPickems(Set<Pickem> pickems) {
        this.pickems = pickems;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}