package com.social_app.service;

import com.social_app.models.Reel;
import com.social_app.models.User;

import java.util.List;

public interface ReelService {
    public Reel createReel(Reel reel, User userId);
    public List<Reel> findAllReels();

    public List<Reel> findUserReels(Integer userId) throws Exception;
}
