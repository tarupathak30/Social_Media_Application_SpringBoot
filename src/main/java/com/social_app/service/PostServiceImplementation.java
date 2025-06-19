package com.social_app.service;

import com.social_app.models.Post;
import com.social_app.models.User;
import com.social_app.repository.PostRepository;
import com.social_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService{
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public Post CreatePost(Post post, Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setImageUrl(post.getImageUrl());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setVideoUrl(post.getVideoUrl());
        newPost.setUser(user);
        postRepository.save(newPost);
        userRepository.save(user);
        return newPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostByPostId(postId);
        User user = userService.findUserById(userId);

        if(!Objects.equals(post.getUser().getId(), user.getId())){
            throw new Exception("you can't delete another user's post");
        }
        postRepository.delete(post);
        return "Post Deleted Successfully";
    }

    @Override
    public List<Post> findPostByUserIdFromTheRepository(Integer userId) throws Exception {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostByPostId(Integer postId) throws Exception {
        Optional<Post> post = postRepository.findPostById(postId);
        if(post.isEmpty()){
            throw new Exception("Post not Found " + postId);
        }
        return post.get();
    }

    @Override
    public List<Post> findAllPostToHomePage() {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {
        Post post = findPostByPostId(postId);
        User user = userService.findUserById(userId);
        if(user.getSavedPosts().contains(post)){
            user.getSavedPosts().remove(post);
        }else{
            user.getSavedPosts().add(post);
        }
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likedPost(Integer userId, Integer postId) throws Exception {
        Post post = findPostByPostId(postId);
        User user = userService.findUserById(userId);

        if(post.getLiked().contains(user)){
            post.getLiked().remove(user);
        }else{
            post.getLiked().add(user);
        }
        return postRepository.save(post);
    }
}
