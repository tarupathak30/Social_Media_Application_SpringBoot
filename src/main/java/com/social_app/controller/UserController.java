package com.social_app.controller;

import com.social_app.models.User;
import com.social_app.repository.PostRepository;
import com.social_app.repository.UserRepository;
import com.social_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @GetMapping("/api/users")
    public List<User> getUser() {
        return userRepository.findAll();
    }


    @GetMapping("/api/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) throws Exception {
        User user1 = userService.findUserById(userId);
        return new ResponseEntity<>(user1, HttpStatus.FOUND);
    }



    @PutMapping("/api/users/{userId}")
    public User updateUser(@RequestBody User user,
                           @PathVariable("userId") Integer userId) throws Exception {
        return userService.updateUser(user, userId);
    }

    @DeleteMapping("users/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId) throws Exception {
        Optional<User> toBeDeletedUser = userRepository.findById(userId);
        if(toBeDeletedUser.isEmpty()){
            throw new Exception("User Doesn't Exist");
        }
        userRepository.delete(toBeDeletedUser.get());
        return "User Deleted Successfully with id " + userId;
    }

    @PutMapping("/api/users/follow/{requestedId}")
    public User followUserHandler(@RequestHeader("Authorization") String token, @PathVariable Integer requestedId) throws Exception {
        Optional<User> requester = userService.findUserByJwt(token);
        if(requester.isEmpty()){
            return null;
        }
        return userService.followUser(requester.get().getId(), requestedId);
    }

    @PutMapping("/api/users")
    public User updateUserFromToken(@RequestHeader("Authorization") String token,
                           @RequestBody User user) throws Exception {
        Optional<User> requestedUser = userService.findUserByJwt(token);
        return userService.updateUser(user, requestedUser.get().getId());
    }
    @GetMapping("/api/users/search")
    public List<User> searchUser(@RequestParam("query") String query){
        return userService.searchUser(query);
    }


    @GetMapping("/api/users/profile")
    public Optional<User> generateUserFromToken(@RequestHeader("Authorization") String token){
        return userService.findUserByJwt(token);
    }
}
