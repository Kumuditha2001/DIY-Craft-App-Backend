package com.craft.craft.LearningProgress.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProgressUpdateRequest {
    @NotBlank(message = "User ID is required")
    private String userId;
    
    private String courseId;
    
    @NotBlank(message = "Lesson ID is required")
    private String lessonId;
    
    @Min(value = 0, message = "Quiz score must be between 0 and 100")
    @Max(value = 100, message = "Quiz score must be between 0 and 100")
    private int quizScore;

    private String notes;
    
    public ProgressUpdateRequest() {}
    
    public ProgressUpdateRequest(String userId, String courseId, String lessonId, 
                               int quizScore, String notes) {
        this.userId = userId;
        this.courseId = courseId;
        this.lessonId = lessonId;
        this.quizScore = quizScore;
        this.notes = notes;
    }
    
    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getLessonId() { return lessonId; }
    public void setLessonId(String lessonId) { this.lessonId = lessonId; }
    public int getQuizScore() { return quizScore; }
    public void setQuizScore(int quizScore) { this.quizScore = quizScore; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}