package com.social_app.service;

import com.social_app.models.Comment;
import com.social_app.models.Post;
import com.social_app.models.User;
import com.social_app.repository.CommentRepository;
import com.social_app.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService{
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        Post post = postService.findPostByPostId(postId);

        Comment savedComment = new Comment();
        savedComment.setUser(user);
        savedComment.setPost(post);
        savedComment.setCreatedAt(LocalDateTime.now());
        savedComment.setContent(comment.getContent());

        post.getComments().add(savedComment);
        postRepository.save(post);

        return commentRepository.save(savedComment);
    }

    @Override
    public Comment findCommentById(Integer commentId) throws Exception {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()){
            throw new Exception("No comment for the given id " + commentId);
        }
        return comment.get();
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws Exception {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId);

        if(!comment.getLikedBy().contains(user)){
            comment.getLikedBy().add(user);
        }else{
            comment.getLikedBy().remove(user);
        }
        return commentRepository.save(comment);
    }

    @Override
    public Comment editComment(Comment comment, Integer commentId) throws Exception {
        Comment editedComment = findCommentById(commentId);

        editedComment.setPost(comment.getPost());
        editedComment.setLikedBy(comment.getLikedBy());
        editedComment.setContent(comment.getContent());
        editedComment.setCreatedAt(comment.getCreatedAt());
        editedComment.setId(comment.getId());

        return commentRepository.save(editedComment);
    }

    @Override
    public Comment deleteComment(Integer commentId) throws Exception {
        Comment comment = findCommentById(commentId);
        commentRepository.delete(comment);
        return comment;
    }
}
