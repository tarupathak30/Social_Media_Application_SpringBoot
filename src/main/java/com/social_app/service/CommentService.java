package com.social_app.service;

import com.social_app.models.Comment;

public interface CommentService {
    public Comment createComment(Comment comment,
                                 Integer postId,
                                 Integer userId);
    public Comment findCommentById(Integer commentId);
    public Comment likeComment(Integer commentId, Integer userId);
}
