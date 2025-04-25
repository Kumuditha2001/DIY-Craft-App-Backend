//package com.craft.craft.post.controller;
//
//import com.craft.craft.post.dto.PostResponse;
//import com.craft.craft.post.service.PostService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/posts")
//@RequiredArgsConstructor
//public class PostController {
//
//    private final PostService postService;
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public PostResponse createPost(
//            @RequestParam String public_id,
//            @RequestParam String caption) {
//        return postService.createPost(public_id, caption);
//    }
//
//    @GetMapping
//    public List<PostResponse> getAllPosts() {
//        return postService.getAllPosts();
//    }
//
//    @GetMapping("/{id}")
//    public PostResponse getPostById(@PathVariable String id) {
//        return postService.getPostById(id);
//    }
//
//    @GetMapping("/user/{public_id}")
//    public List<PostResponse> getPostsByPublicId(@PathVariable String public_id) {
//        return postService.getPostsByPublicId(public_id);
//    }
//}
