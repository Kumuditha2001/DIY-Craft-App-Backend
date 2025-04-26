package com.craft.craft.Learning_Plan_Sharing.repository;

import com.craft.craft.Learning_Plan_Sharing.model.LearnModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearnRepository extends MongoRepository<LearnModel, ObjectId> {
    Optional<LearnModel> findByTitle(String title);
}