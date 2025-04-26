package com.craft.craft.LearningProgress.controller;

import com.craft.craft.LearningProgress.dto.request.ProgressUpdateRequest;
import com.craft.craft.LearningProgress.dto.response.CourseProgressResponse;
import com.craft.craft.LearningProgress.dto.response.LearningStatsResponse;
import com.craft.craft.LearningProgress.model.LearningProgress;
import com.craft.craft.LearningProgress.service.LearningProgressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "*")
public class LearningProgressController {

    private final LearningProgressService progressService;
    
    @Autowired
    public LearningProgressController(LearningProgressService progressService) {
        this.progressService = progressService;
    }
    
    @PostMapping("/lesson/start")
    public ResponseEntity<LearningProgress> startLesson(@Valid @RequestBody ProgressUpdateRequest request) {
        LearningProgress progress = progressService.recordLessonStart(
            request.getUserId(), 
            request.getCourseId(), 
            request.getLessonId()
        );
        return new ResponseEntity<>(progress, HttpStatus.OK);
    }
    
    @PostMapping("/lesson/complete")
    public ResponseEntity<LearningProgress> completeLesson(@Valid @RequestBody ProgressUpdateRequest request) {
        LearningProgress progress = progressService.completeLessonProgress(
            request.getUserId(),
            request.getLessonId(),
            request.getQuizScore()
        );
        return new ResponseEntity<>(progress, HttpStatus.OK);
    }
    
    @PutMapping("/lesson/notes")
    public ResponseEntity<LearningProgress> updateLessonNotes(@Valid @RequestBody ProgressUpdateRequest request) {
        LearningProgress progress = progressService.updateLessonNotes(
            request.getUserId(),
            request.getLessonId(),
            request.getNotes()
        );
        return new ResponseEntity<>(progress, HttpStatus.OK);
    }
    
    @GetMapping("/course/{userId}/{courseId}")
    public ResponseEntity<CourseProgressResponse> getCourseProgress(
            @PathVariable String userId,
            @PathVariable String courseId) {
        CourseProgressResponse progress = progressService.getCourseProgress(userId, courseId);
        return new ResponseEntity<>(progress, HttpStatus.OK);
    }
    
    @GetMapping("/stats/{userId}")
    public ResponseEntity<LearningStatsResponse> getLearningStats(@PathVariable String userId) {
        LearningStatsResponse stats = progressService.getUserLearningStats(userId);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LearningProgress>> getUserProgress(@PathVariable String userId) {
        List<LearningProgress> progressList = progressService.getProgressByUserId(userId);
        return new ResponseEntity<>(progressList, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/course/{courseId}")
    public ResponseEntity<List<LearningProgress>> getUserCourseProgress(
            @PathVariable String userId,
            @PathVariable String courseId) {
        List<LearningProgress> progressList = progressService.getProgressByUserAndCourse(userId, courseId);
        return new ResponseEntity<>(progressList, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/completed")
    public ResponseEntity<List<LearningProgress>> getUserCompletedLessons(@PathVariable String userId) {
        List<LearningProgress> completedLessons = progressService.getCompletedLessonsByUser(userId);
        return new ResponseEntity<>(completedLessons, HttpStatus.OK);
    }
    
    @GetMapping("/lesson/{userId}/{lessonId}")
    public ResponseEntity<LearningProgress> getLessonProgress(
            @PathVariable String userId,
            @PathVariable String lessonId) {
        LearningProgress progress = progressService.getProgressByUserAndLesson(userId, lessonId);
        return new ResponseEntity<>(progress, HttpStatus.OK);
    }
    
    @DeleteMapping("/{progressId}")
    public ResponseEntity<Void> deleteProgress(@PathVariable String progressId) {
        progressService.deleteProgress(progressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}