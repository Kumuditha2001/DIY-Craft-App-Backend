package com.craft.craft.Learning_Plan_Sharing.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.craft.craft.Learning_Plan_Sharing.model.LearningPlan;

public interface LearningRepository extends MongoRepository<LearningPlan, ObjectId> {

}
