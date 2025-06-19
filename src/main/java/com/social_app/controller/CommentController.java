package com.social_app.controller;

import com.social_app.models.Comment;
import com.social_app.models.User;
import com.social_app.service.CommentService;
import com.social_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/comments/post/{postId}")
    public Comment createComment(
            @RequestHeader("Authorization") String token,
            @RequestBody Comment comment,
            @PathVariable("postId") Integer postId) throws Exception {

        Optional<User> user = userService.findUserByJwt(token);
        return commentService.createComment(comment, postId, user.get().getId());
    }

    @PutMapping("/api/comments/like/{commentId}")
    public Comment likeComment(@RequestHeader("Authorization") String token,
                               @PathVariable("commentId") Integer commentId) throws Exception {
        Optional<User> user = userService.findUserByJwt(token);
        return commentService.likeComment(commentId, user.get().getId());
    }
}

