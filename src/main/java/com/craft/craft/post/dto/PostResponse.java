//package com.craft.craft.post.dto;
//
//import com.craft.craft.post.model.Post;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class PostResponse {
//    private String id;
//    private String public_id;
//    private String caption;
//    private Date createdAt;
//
//    public static PostResponse fromEntity(Post post) {
//        return new PostResponse(
//                post.getId(),
//                post.getPublic_id(),
//                post.getCaption(),
//                post.getCreatedAt()
//        );
//    }
//}