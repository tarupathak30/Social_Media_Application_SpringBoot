package com.social_app.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private String video;

    @ManyToOne
    private User user;

    private LocalDateTime createdAt;

    public Reel(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Reel(Integer id, String title, String video, User user, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.video = video;
        this.user = user;
        this.createdAt = createdAt;
    }
}
