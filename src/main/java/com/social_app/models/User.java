package com.social_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Integer> followers = new ArrayList<>(); //it will store list of user ID
    private List<Integer> followings = new ArrayList<>(); //it will store list of user ID
    private String gender;
    @ManyToMany
    @JoinTable(
            name = "saved_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    @JsonIgnore
    private List<Post> savedPosts = new ArrayList<>();
    public void setFollowers(List<Integer> followers) {
        this.followers = followers;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFollowings(List<Integer> followings) {
        this.followings = followings;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public User(Integer id, String firstName, String lastName, String email, String password, List<Integer> followers, List<Integer> followings, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.followers = followers;
        this.followings = followings;
        this.gender = gender;
    }

    public User(){}


    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Integer> getFollowers() {
        return followers;
    }

    public List<Integer> getFollowings() {
        return followings;
    }

    public String getGender() {
        return gender;
    }

    public List<Post> getSavedPosts() {
        return savedPosts;
    }
}
