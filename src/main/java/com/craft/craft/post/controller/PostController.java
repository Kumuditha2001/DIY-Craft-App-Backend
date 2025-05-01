package com.craft.craft.post.controller;

import com.cloudinary.utils.ObjectUtils;
import com.craft.craft.post.model.Post;
import com.craft.craft.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;

import java.io.IOException;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private Cloudinary cloudinary;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(
            @RequestParam(value = "caption", required = false) String caption,
            @RequestParam(value = "media", required = false) MultipartFile mediaFile) {

        try {
            Post post = new Post();
            post.setCaption(caption);

            if (mediaFile != null && !mediaFile.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(mediaFile.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                String mediaUrl = (String) uploadResult.get("secure_url");
                post.setMediaUrls(List.of(mediaUrl));
            }

            Post savedPost = postService.createPost(post);
            return ResponseEntity.ok(savedPost);

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Failed to upload media: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
}