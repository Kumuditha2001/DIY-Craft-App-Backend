package com.craft.craft.Learning_Plan_Sharing.controller;

import com.craft.craft.Learning_Plan_Sharing.model.LearnModel;
import com.craft.craft.Learning_Plan_Sharing.service.LearnServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/learn")
public class LearnController {

    @Autowired
    private LearnServices learnServices;

    // Create
    @PostMapping
    public ResponseEntity<LearnModel> createLearn(@RequestBody LearnModel learnModel) {
        LearnModel createdLearn = learnServices.createLearn(learnModel);
        return new ResponseEntity<>(createdLearn, HttpStatus.CREATED);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<LearnModel>> getAllLearns() {
        List<LearnModel> learns = learnServices.getAllLearns();
        return new ResponseEntity<>(learns, HttpStatus.OK);
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<LearnModel> getLearnById(@PathVariable("id") String id) {
        Optional<LearnModel> learn = learnServices.getLearnById(new ObjectId(id));
        return learn.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Read by title
    @GetMapping("/title/{title}")
    public ResponseEntity<LearnModel> getLearnByTitle(@PathVariable("title") String title) {
        Optional<LearnModel> learn = learnServices.getLearnByTitle(title);
        return learn.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<LearnModel> updateLearn(@PathVariable("id") String id, @RequestBody LearnModel learnModel) {
        Optional<LearnModel> existingLearn = learnServices.getLearnById(new ObjectId(id));

        if (existingLearn.isPresent()) {
            learnModel.setId(new ObjectId(id));
            LearnModel updatedLearn = learnServices.updateLearn(learnModel);
            return new ResponseEntity<>(updatedLearn, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLearn(@PathVariable("id") String id) {
        try {
            learnServices.deleteLearn(new ObjectId(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}