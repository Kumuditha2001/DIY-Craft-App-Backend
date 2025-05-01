package com.craft.craft.post.repository;

import com.craft.craft.post.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}