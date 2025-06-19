package com.social_app.repository;

import com.social_app.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select p from Post p where p.user.id = :userId")
    List<Post> findPostByUserId(Integer userId);

    Optional<Post> findPostById(Integer postId);

}
