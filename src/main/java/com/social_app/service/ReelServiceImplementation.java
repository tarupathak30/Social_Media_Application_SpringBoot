package com.social_app.service;

import com.social_app.models.Reel;
import com.social_app.models.User;
import com.social_app.repository.ReelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReelServiceImplementation implements ReelService{

    @Autowired
    private ReelRepository reelRepository;


    @Autowired
    private UserService userService;


    @Override
    public Reel createReel(Reel reel, User user) {
        Reel createdReel = new Reel();

        createdReel.setUser(user);
        createdReel.setCreatedAt(LocalDateTime.now());
        createdReel.setTitle(reel.getTitle());
        createdReel.setVideo(reel.getVideo());

        return reelRepository.save(createdReel);
    }

    @Override
    public List<Reel> findAllReels() {
        return reelRepository.findAll();
    }

    @Override
    public List<Reel> findUserReels(Integer userId) throws Exception {
        return reelRepository.findByUserId(userId);
    }
}
