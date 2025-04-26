package com.craft.craft.LearningProgress.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "learning_progress")
public class LearningProgress {
    @Id
    private String id;
    
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotBlank(message = "Course ID is required")
    private String courseId;
    
    @NotBlank(message = "Lesson ID is required")
    private String lessonId;
    
    private boolean completed;
    private LocalDateTime startDate;
    private LocalDateTime completionDate;
    private int quizScore;
    private long timeSpentSeconds;
    private List<String> achievementIds = new ArrayList<>();
    private String notes;
    
    public LearningProgress() {}
    
    public LearningProgress(String userId, String courseId, String lessonId) {
        this.userId = userId;
        this.courseId = courseId;
        this.lessonId = lessonId;
        this.startDate = LocalDateTime.now();
        this.completed = false;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getLessonId() { return lessonId; }
    public void setLessonId(String lessonId) { this.lessonId = lessonId; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDateTime completionDate) { this.completionDate = completionDate; }
    public int getQuizScore() { return quizScore; }
    public void setQuizScore(int quizScore) { this.quizScore = quizScore; }
    public long getTimeSpentSeconds() { return timeSpentSeconds; }
    public void setTimeSpentSeconds(long timeSpentSeconds) { this.timeSpentSeconds = timeSpentSeconds; }
    public List<String> getAchievementIds() { return achievementIds; }
    public void setAchievementIds(List<String> achievementIds) { this.achievementIds = achievementIds; }
    public void addAchievement(String achievementId) { this.achievementIds.add(achievementId); }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}