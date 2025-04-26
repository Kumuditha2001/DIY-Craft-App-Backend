package com.craft.craft.LearningProgress.dto.response;

import java.util.HashMap;
import java.util.Map;

public class LearningStatsResponse {
    private long completedLessons;
    private double averageQuizScore;
    private long totalTimeSpentSeconds;
    private int achievementsEarned;
    private Map<String, Integer> coursesProgress = new HashMap<>();
    
    public LearningStatsResponse() {}
    
    public LearningStatsResponse(long completedLessons, double averageQuizScore, 
                               long totalTimeSpentSeconds) {
        this.completedLessons = completedLessons;
        this.averageQuizScore = averageQuizScore;
        this.totalTimeSpentSeconds = totalTimeSpentSeconds;
    }
    
    // Getters and Setters
    public long getCompletedLessons() { return completedLessons; }
    public void setCompletedLessons(long completedLessons) { this.completedLessons = completedLessons; }
    public double getAverageQuizScore() { return averageQuizScore; }
    public void setAverageQuizScore(double averageQuizScore) { this.averageQuizScore = averageQuizScore; }
    public long getTotalTimeSpentSeconds() { return totalTimeSpentSeconds; }
    public void setTotalTimeSpentSeconds(long totalTimeSpentSeconds) { this.totalTimeSpentSeconds = totalTimeSpentSeconds; }
    public int getAchievementsEarned() { return achievementsEarned; }
    public void setAchievementsEarned(int achievementsEarned) { this.achievementsEarned = achievementsEarned; }
    public Map<String, Integer> getCoursesProgress() { return coursesProgress; }
    public void setCoursesProgress(Map<String, Integer> coursesProgress) { this.coursesProgress = coursesProgress; }
    public void addCourseProgress(String courseId, int progressPercentage) { this.coursesProgress.put(courseId, progressPercentage); }
}