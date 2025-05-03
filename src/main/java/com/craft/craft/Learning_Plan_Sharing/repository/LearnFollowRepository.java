package com.craft.craft.Learning_Plan_Sharing.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.craft.craft.Learning_Plan_Sharing.model.LearnFollow;

@Repository
public interface LearnFollowRepository extends MongoRepository<LearnFollow, ObjectId> {
    LearnFollow findByPlanIdAndUserId(String planId, String userId);

    Optional<LearnFollow> findByUserIdAndPlanId(String userId, String planId);
}
