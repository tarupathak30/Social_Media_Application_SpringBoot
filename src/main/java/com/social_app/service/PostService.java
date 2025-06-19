package com.social_app.service;

import com.social_app.models.Post;
import com.social_app.models.User;

import java.util.List;

public interface PostService {
    Post CreatePost(Post post, Integer userId) throws Exception;
    String deletePost(Integer postId, Integer userId) throws Exception;
    List<Post> findPostByUserIdFromTheRepository(Integer userId) throws Exception;
    Post findPostByPostId(Integer postId) throws Exception;
    List<Post> findAllPostToHomePage();
    Post savedPost(Integer postId, Integer userId) throws Exception;
    Post likedPost(Integer userId, Integer postId) throws Exception;
}
