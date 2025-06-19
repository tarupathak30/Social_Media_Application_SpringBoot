package com.social_app.controller;

import com.social_app.config.JwtProvider;
import com.social_app.models.Post;
import com.social_app.models.User;
import com.social_app.repository.UserRepository;
import com.social_app.response.ApiResponse;
import com.social_app.service.PostService;
import com.social_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(
            @RequestHeader("Authorization") String token,
            @RequestBody Post post) throws Exception {
        Optional<User> requestingUser = userService.findUserByJwt(token);
        Post newPost = postService.CreatePost(post, requestingUser.get().getId());
        return new ResponseEntity<>(newPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@RequestHeader("Authorization") String token,
                                                  @PathVariable Integer postId) throws Exception {
        Optional<User> userWhoOwnsThePost = userService.findUserByJwt(token);
        String message = postService.deletePost(postId, userWhoOwnsThePost.get().getId());

        ApiResponse res = new ApiResponse(message, true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> findPostByPostIdHandler(@PathVariable Integer postId) throws Exception {
        Post post = postService.findPostByPostId(postId);
        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> fetchAllPostsToHomePage(){
        return new ResponseEntity<>(postService.findAllPostToHomePage(), HttpStatus.FOUND);
    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<Post>> findUsersPost(@PathVariable Integer userId) throws Exception {
        return new ResponseEntity<List<Post>>(postService.findPostByUserIdFromTheRepository(userId), HttpStatus.OK);
    }

    @PutMapping("/posts/save/{postId}")
    public ResponseEntity<Post> savedPostByUser(@RequestHeader("Authorization") String token,
                                                @PathVariable Integer postId) throws Exception {
        Optional<User> user = userService.findUserByJwt(token);
        return new ResponseEntity<Post>(postService.savedPost(postId, user.get().getId()), HttpStatus.ACCEPTED);
    }

    @PutMapping("/posts/like/{postId}")
    public ResponseEntity<Post> likedByUser(@RequestHeader("Authorization") String token,
                                            @PathVariable Integer postId) throws Exception {
        Optional<User> user = userService.findUserByJwt(token);
        return new ResponseEntity<Post>(postService.likedPost(user.get().getId(), postId), HttpStatus.OK);
    }
}
