package com.craft.craft.Learning_Plan_Sharing.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.craft.craft.Learning_Plan_Sharing.model.LearningPlan;
import com.craft.craft.Learning_Plan_Sharing.service.LearningPlanService;

@RestController
@CrossOrigin
@RequestMapping("/api/learning")
public class LearningPlanController {

    @Autowired
    private LearningPlanService LearninPlan_Service;

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> createLearningPlan(
            @RequestParam("userId") String userId,
            @RequestParam("profilPic") String profilPic,
            @RequestParam("headline") String headline,
            @RequestParam("fullname") String fullname,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("timeline") String timeline, 
            @RequestParam(value = "thumbnail") MultipartFile thumbnail,
            @RequestParam(value = "video") MultipartFile video) {

        Map<String, Object> response = new HashMap<>();

        try {
            LearningPlan createdPlan = LearninPlan_Service.createLearningPlan(
                    userId, profilPic, headline, fullname, title, description, timeline, thumbnail, video);

            response.put("message", "Learning plan created successfully!");
            response.put("data", createdPlan);
            return ResponseEntity.status(201).body(response); // HTTP 201 Created

        } catch (Exception e) {
            response.put("message", "Error creating learning plan: " + e.getMessage());
            return ResponseEntity.status(500).body(response); // HTTP 500 Internal Server Error
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<LearningPlan>> getAllPlans() {
        return ResponseEntity.ok(LearninPlan_Service.getAllPlans());
    }

    @PutMapping(value = "/update", consumes = "multipart/form-data")
    public ResponseEntity<?> updateLearningPlan(
            @RequestParam("id") String id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("timeline") String timeline,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestParam(value = "video", required = false) MultipartFile video) {

        try {
            LearningPlan updated = LearninPlan_Service.updateLearningPlan(id, title, description,timeline, thumbnail, video);
            return ResponseEntity.ok(Map.of("message", "Learning plan updated successfully!", "data", updated));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error updating learning plan: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePlan(
            @RequestParam("id") String id) {

        boolean deleted = LearninPlan_Service.deletePlanById(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Learning plan deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Plan not found or userId mismatch"));
        }
    }
}
