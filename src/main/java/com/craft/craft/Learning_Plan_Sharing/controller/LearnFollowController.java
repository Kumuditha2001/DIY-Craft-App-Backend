package com.craft.craft.Learning_Plan_Sharing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.craft.craft.Learning_Plan_Sharing.model.LearnFollow;
import com.craft.craft.Learning_Plan_Sharing.service.LearnFollowService;

@RestController
@CrossOrigin
@RequestMapping("/api/planFollow")
public class LearnFollowController {

    @Autowired
    private LearnFollowService learn_follow_service;

    @PostMapping("/insert")
    public ResponseEntity<?> insertFollow(@RequestBody LearnFollow follow) {
        try {
            LearnFollow savedFollow = learn_follow_service.insertFollow(follow);
            return ResponseEntity.ok(savedFollow);
        } catch (Exception e) {
            if (e.getMessage().contains("already enrolled")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is already enrolled in this plan.");
            }
            return ResponseEntity.internalServerError().body("An unexpected error occurred.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LearnFollow> updateFollow(
            @PathVariable String id,
            @RequestBody LearnFollow updatedFollow) {
        LearnFollow updated = learn_follow_service.updateFollow(id, updatedFollow);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{planId}/{userId}")
    public ResponseEntity<LearnFollow> getFollowByPlanIdAndUserId(
            @PathVariable String planId,
            @PathVariable String userId) {
        LearnFollow follow = learn_follow_service.getFollowByPlanIdAndUserId(planId, userId);
        if (follow != null) {
            return ResponseEntity.ok(follow);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
