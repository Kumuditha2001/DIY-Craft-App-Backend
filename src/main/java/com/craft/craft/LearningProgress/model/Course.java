package com.craft.craft.LearningProgress.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "courses")
public class Course {
    @Id
    private String id;
    
    @NotBlank(message = "Course title is required")
    private String title;
    
    private String description;
    private String imageUrl;
    private String category;
    private int difficulty;
    private List<Lesson> lessons = new ArrayList<>();
    
    public Course() {}
    
    public Course(String title, String description, String imageUrl, 
                 String category, int difficulty) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.difficulty = difficulty;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
    public List<Lesson> getLessons() { return lessons; }
    public void setLessons(List<Lesson> lessons) { this.lessons = lessons; }
    public void addLesson(Lesson lesson) { this.lessons.add(lesson); }
}