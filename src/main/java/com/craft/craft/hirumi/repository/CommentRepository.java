package com.craft.craft.hirumi.repository;

import com.craft.craft.hirumi.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(String postId);
    List<Comment> findByPostIdAndUserId(String postId, String userId);

}
