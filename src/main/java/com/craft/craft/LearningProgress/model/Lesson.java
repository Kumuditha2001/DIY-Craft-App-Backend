package com.craft.craft.LearningProgress.model;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class Lesson {
    @Id
    private String id;
    
    @NotBlank(message = "Lesson title is required")
    private String title;
    
    private String description;
    private String content;
    private int orderIndex;
    private int duration;
    private List<String> materials = new ArrayList<>();
    private List<String> requirements = new ArrayList<>();
    
    public Lesson() {}
    
    public Lesson(String title, String description, String content, 
                 int orderIndex, int duration) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.orderIndex = orderIndex;
        this.duration = duration;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getOrderIndex() { return orderIndex; }
    public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public List<String> getMaterials() { return materials; }
    public void setMaterials(List<String> materials) { this.materials = materials; }
    public List<String> getRequirements() { return requirements; }
    public void setRequirements(List<String> requirements) { this.requirements = requirements; }
}