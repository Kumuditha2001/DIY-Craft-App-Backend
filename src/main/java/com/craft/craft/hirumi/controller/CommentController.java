package com.craft.craft.hirumi.controller;

import com.craft.craft.hirumi.model.Comment;
import com.craft.craft.hirumi.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        try {
            logger.info("Received request to create comment: {}", comment);
            
            // Validate input
            if (comment.getPostId() == null || comment.getUserId() == null || comment.getText() == null) {
                logger.error("Invalid comment data: missing required fields");
                return ResponseEntity.badRequest().body("Required fields missing (postId, userId, text)");
            }
            
            Comment savedComment = commentRepository.save(comment);
            logger.info("Comment created successfully with ID: {}", savedComment.getId());
            return ResponseEntity.ok(savedComment);
        } catch (Exception e) {
            logger.error("Error creating comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating comment: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        try {
            List<Comment> comments = commentRepository.findAll();
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            logger.error("Error fetching comments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/post/{postId}")
public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable String postId) {
    try {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return ResponseEntity.ok(comments);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@PutMapping("/post/{postId}/user/{userId}")
public ResponseEntity<?> updateCommentByPostId(
        @PathVariable String postId,
        @PathVariable String userId,
        @RequestBody Comment updatedComment) {

    try {
        
        List<Comment> userComments = commentRepository.findByPostIdAndUserId(postId, userId);

        if (userComments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }

        
        Comment existingComment = userComments.get(0);

        
        existingComment.setText(updatedComment.getText());

        
        Comment savedComment = commentRepository.save(existingComment);
        return ResponseEntity.ok(savedComment);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error updating comment: " + e.getMessage());
    }
}

@DeleteMapping("/post/{postId}")
public ResponseEntity<?> deleteCommentByPostId(
        @PathVariable String postId,
        @RequestParam String userId) {
    
    try {
        
        List<Comment> comments = commentRepository.findByPostId(postId);

        
        Optional<Comment> commentOpt = comments.stream()
            .filter(comment -> comment.getUserId().equals(userId))
            .findFirst();

        if (commentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No comment found by this user for the post");
        }

        Comment comment = commentOpt.get();

       
        commentRepository.deleteById(comment.getId());
        return ResponseEntity.ok("Comment deleted successfully");

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error deleting comment: " + e.getMessage());
    }
}


}