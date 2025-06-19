package com.social_app.service;

import com.social_app.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User registerUser(User user);
    public User findUserById(Integer userId) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User followUser(Integer requestUserId, Integer followUserId) throws Exception;
    public User updateUser(User user, Integer userId) throws Exception;
    public List<User> searchUser(String query);
    public Optional<User> findUserByJwt(String token);
}
