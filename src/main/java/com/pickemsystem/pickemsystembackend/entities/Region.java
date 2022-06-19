package com.pickemsystem.pickemsystembackend.entities;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "regions")
@EqualsAndHashCode
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    public Integer getId() {
        return id;
    }

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "image_url", nullable = false, length = 120)
    private String imageUrl;

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}