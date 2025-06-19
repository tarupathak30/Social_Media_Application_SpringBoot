package com.social_app.controller;

import com.social_app.config.JwtProvider;
import com.social_app.models.Reel;
import com.social_app.models.User;
import com.social_app.service.ReelService;
import com.social_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReelController {
    @Autowired
    private ReelService reelService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/reels")
    public Reel createReel(
            @RequestHeader("Authorization") String token,
            @RequestBody Reel reel){
        Optional<User> user = userService.findUserByJwt(token);
        return reelService.createReel(reel, user.get());
    }

    @GetMapping("/api/reels")
    public List<Reel> findAllReels(){
        return reelService.findAllReels();
    }

    @GetMapping("/api/reels/user")
    public List<Reel> findAllReelsOfUser(@RequestHeader("Authorization") String token) throws Exception {
        Optional<User> user = userService.findUserByJwt(token);
        return reelService.findUserReels(user.get().getId());
    }
}
