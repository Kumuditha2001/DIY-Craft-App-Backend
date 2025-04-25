//package com.craft.craft.post.service;
//
//import com.craft.craft.post.dto.PostResponse;
//import com.craft.craft.post.model.Post;
//import com.craft.craft.post.repository.PostRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class PostService {
//
//    private final PostRepository postRepository;
//
//    public PostResponse createPost(String public_id, String caption) {
//        Post post = new Post();
//        post.setPublic_id(public_id);
//        post.setCaption(caption);
//        post.setCreatedAt(new Date());
//
//        Post savedPost = postRepository.save(post);
//        return PostResponse.fromEntity(savedPost);
//    }
//
//    public List<PostResponse> getAllPosts() {
//        return postRepository.findAll()
//                .stream()
//                .map(PostResponse::fromEntity)
//                .collect(Collectors.toList());
//    }
//
//    public List<PostResponse> getPostsByPublicId(String public_id) {
//        return postRepository.findByPublic_id(public_id)
//                .stream()
//                .map(PostResponse::fromEntity)
//                .collect(Collectors.toList());
//    }
//
//    public PostResponse getPostById(String id) {
//        return postRepository.findById(id)
//                .map(PostResponse::fromEntity)
//                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
//    }
//}