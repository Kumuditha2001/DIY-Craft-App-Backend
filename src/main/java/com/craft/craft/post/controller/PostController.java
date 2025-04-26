package com.craft.craft.post.controller;

import com.craft.craft.post.model.Post;
import com.craft.craft.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Get all posts
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Get post by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable String id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Post not found with id: " + id);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Create post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post savedPost = postService.createPost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    // Update post
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable String id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id, post);
        if (updatedPost != null) {
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Post not found with id: " + id);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Delete post
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id) {
        boolean deleted = postService.deletePost(id);
        if (deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Post deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Post not found with id: " + id);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}