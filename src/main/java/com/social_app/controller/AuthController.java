package com.social_app.controller;

import com.social_app.config.JwtProvider;
import com.social_app.models.User;
import com.social_app.repository.UserRepository;
import com.social_app.request.LoginRequest;
import com.social_app.response.AuthResponse;
import com.social_app.service.CustomUserDetailsService;
import com.social_app.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @PostMapping("/signup")
    @Transactional
    public AuthResponse createUser(@RequestBody User user) throws Exception {
        Optional<User> isExist = userRepository.findByEmail(user.getEmail());
        if(isExist.isPresent()){
            throw new Exception("this email is already is used with another account");
        }
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());

        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getEmail(), newUser.getPassword());

        String token = JwtProvider.generateToken(authentication);
        userRepository.save(newUser);
        return new AuthResponse(token, "Register Success");
    }


    @PostMapping("/signin")
    public AuthResponse sigin(@RequestBody LoginRequest loginRequest){
        Authentication authentication =
                authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String token = JwtProvider.generateToken(authentication);


        return new AuthResponse(token, "Register Success");
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid Username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Password not Matched");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
