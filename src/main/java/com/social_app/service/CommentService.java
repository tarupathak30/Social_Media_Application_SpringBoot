package com.social_app.service;

import com.social_app.models.Comment;

public interface CommentService {
    public Comment createComment(Comment comment,
                                 Integer postId,
                                 Integer userId) throws Exception;
    public Comment findCommentById(Integer commentId) throws Exception;
    public Comment likeComment(Integer commentId, Integer userId) throws Exception;

    public Comment editComment(Comment comment, Integer commentId) throws Exception;
    public Comment deleteComment(Integer commentId) throws Exception;
}
