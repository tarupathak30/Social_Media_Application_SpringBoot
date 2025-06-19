package com.social_app.repository;

import com.social_app.models.Reel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelRepository extends JpaRepository<Reel, Integer> {
    public List<Reel> findByUserId(Integer userId);
}
