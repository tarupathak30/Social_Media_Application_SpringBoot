package com.social_app.service;

import com.social_app.config.JwtProvider;
import com.social_app.models.User;
import com.social_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    UserRepository userRepository;
    @Override
    public User registerUser(User user) {
        User newUser = new User();
        newUser.setPassword(user.getPassword());
        newUser.setId(user.getId());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new Exception("User does not exist with User Id " + userId);
        }
        return user.get();
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User with email " + email + " does not exist")));
        return user.get();
    }

    @Override
    public User followUser(Integer requesterId, Integer requestedId) throws Exception {
        User requesterUser = findUserById(requesterId);
        User requestedUser = findUserById(requestedId);

        requestedUser.getFollowers().add(requesterUser.getId());
        requesterUser.getFollowings().add(requestedUser.getId());

        userRepository.save(requestedUser);
        userRepository.save(requesterUser);
        return requesterUser;
    }

    @Override
    public User updateUser(User user, Integer userId) throws Exception {
        User user1 = findUserById(userId);

        if(user1 == null){
            throw new Exception("User Does not Exist with Id " + userId);
        }


        if(user.getFirstName() != null){
            user1.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null){
            user1.setLastName(user.getLastName());
        }
        if(user.getEmail() != null){
            user1.setEmail(user.getEmail());
        }
        if(user.getPassword() != null){
            user1.setPassword(user.getPassword());
        }
        if(user.getGender() != null){
            user1.setGender(user.getGender());
        }

        return userRepository.save(user1);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

    @Override
    public Optional<User> findUserByJwt(String token){
        String email = JwtProvider.getEmailFromJwtToken(token);
        return userRepository.findByEmail(email);
    }

}
