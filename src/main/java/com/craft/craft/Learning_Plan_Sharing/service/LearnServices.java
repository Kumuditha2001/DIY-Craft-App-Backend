package com.craft.craft.Learning_Plan_Sharing.service;

import com.craft.craft.Learning_Plan_Sharing.model.LearnModel;
import com.craft.craft.Learning_Plan_Sharing.repository.LearnRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearnServices {

    @Autowired
    private LearnRepository learnRepository;

    // Create
    public LearnModel createLearn(LearnModel learnModel) {
        return learnRepository.save(learnModel);
    }

    // Read all
    public List<LearnModel> getAllLearns() {
        return learnRepository.findAll();
    }

    // Read by ID
    public Optional<LearnModel> getLearnById(ObjectId id) {
        return learnRepository.findById(id);
    }

    // Read by title
    public Optional<LearnModel> getLearnByTitle(String title) {
        return learnRepository.findByTitle(title);
    }

    // Update
    public LearnModel updateLearn(LearnModel learnModel) {
        return learnRepository.save(learnModel);
    }

    // Delete
    public void deleteLearn(ObjectId id) {
        learnRepository.deleteById(id);
    }
}