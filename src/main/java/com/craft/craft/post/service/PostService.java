package com.craft.craft.post.service;

import com.craft.craft.post.model.Post;
import com.craft.craft.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(String id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(Post post) {
        if (post.getCreatedAt() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        return postRepository.save(post);
    }

    public Post updatePost(String id, Post post) {
        Post existingPost = postRepository.findById(id).orElse(null);
        if (existingPost != null) {
            existingPost.setCaption(post.getCaption());
            if (post.getMediaUrls() != null) {
                existingPost.setMediaUrls(post.getMediaUrls());
            }
            return postRepository.save(existingPost);
        }
        return null;
    }

    public boolean deletePost(String id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}