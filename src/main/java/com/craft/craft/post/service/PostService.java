package com.craft.craft.post.service;

import com.craft.craft.post.model.Post;
import com.craft.craft.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // Get all posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Get post by ID
    public Post getPostById(String id) {
        return postRepository.findById(id).orElse(null);
    }

    // Create post
    public Post createPost(Post post) {
        // Set creation time if not provided
        if (post.getCreatedAt() == null) {
            post.setCreatedAt(LocalDateTime.now().toString());
        }
        return postRepository.save(post);
    }

    // Update post
    public Post updatePost(String id, Post post) {
        Post existingPost = postRepository.findById(id).orElse(null);
        if (existingPost != null) {
            existingPost.setCaption(post.getCaption());
            return postRepository.save(existingPost);
        }
        return null;
    }

    // Delete post
    public boolean deletePost(String id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}