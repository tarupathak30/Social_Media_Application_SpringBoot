package com.social_app.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;

    //ManyToOne because a single user
    // can comment as many as times
    @ManyToOne
    private User user;

    // a user can like many comment
    // many users can like a single comment
    @ManyToMany
    private List<User> likedBy = new ArrayList<>();

    private LocalDateTime  createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<User> likedBy) {
        this.likedBy = likedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Comment(Integer id, String content, User user, List<User> likedBy, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.likedBy = likedBy;
        this.createdAt = createdAt;
    }
    public Comment(){

    }
}
