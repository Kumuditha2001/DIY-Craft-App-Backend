package com.craft.craft.Learning_Plan_Sharing.service;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craft.craft.Learning_Plan_Sharing.model.LearnFollow;
import com.craft.craft.Learning_Plan_Sharing.repository.LearnFollowRepository;

@Service
public class LearnFollowService {

    @Autowired
    private LearnFollowRepository learnFollowRepo;



    public LearnFollow insertFollow(LearnFollow follow) throws Exception {
        Optional<LearnFollow> existingFollow = learnFollowRepo.findByUserIdAndPlanId(follow.getUserId(), follow.getPlanId());
        if (existingFollow.isPresent()) {
            throw new Exception("User already enrolled in this plan");
        }
        return learnFollowRepo.save(follow);
    }

    
    public LearnFollow updateFollow(String id, LearnFollow updatedData) {
        ObjectId objectId = new ObjectId(id);
        Optional<LearnFollow> existing = learnFollowRepo.findById(objectId);

        if (existing.isPresent()) {
            LearnFollow learnFollow = existing.get();
            learnFollow.setWatchedDuration(updatedData.getWatchedDuration());
            learnFollow.setIsCompleted(updatedData.getIsCompleted());
            learnFollow.setProgress(updatedData.getProgress());
            return learnFollowRepo.save(learnFollow);
        } else {

            return null;
        }
    }

    public LearnFollow getFollowByPlanIdAndUserId(String planId, String userId) {
        return learnFollowRepo.findByPlanIdAndUserId(planId, userId);
    }
}
