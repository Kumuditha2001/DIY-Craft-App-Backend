package com.craft.craft.LearningProgress.dto.response;

public class CourseProgressResponse {
    private String courseId;
    private long completedLessons;
    private long totalLessons;
    private double percentageComplete;
    private boolean isCompleted;
    
    public CourseProgressResponse() {}
    
    public CourseProgressResponse(String courseId, long completedLessons, 
                                long totalLessons, double percentageComplete) {
        this.courseId = courseId;
        this.completedLessons = completedLessons;
        this.totalLessons = totalLessons;
        this.percentageComplete = percentageComplete;
        this.isCompleted = percentageComplete >= 100;
    }
    
    // Getters and Setters
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public long getCompletedLessons() { return completedLessons; }
    public void setCompletedLessons(long completedLessons) { this.completedLessons = completedLessons; }
    public long getTotalLessons() { return totalLessons; }
    public void setTotalLessons(long totalLessons) { this.totalLessons = totalLessons; }
    public double getPercentageComplete() { return percentageComplete; }
    public void setPercentageComplete(double percentageComplete) { this.percentageComplete = percentageComplete; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}