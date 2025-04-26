package com.craft.craft.LearningProgress.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "achievements")
public class Achievement {
    @Id
    private String id;
    
    @NotBlank(message = "Achievement name is required")
    private String name;
    
    private String description;
    private String badgeImageUrl;
    private String criteria;
    private int pointsValue;
    
    public Achievement() {}
    
    public Achievement(String name, String description, String badgeImageUrl, 
                      String criteria, int pointsValue) {
        this.name = name;
        this.description = description;
        this.badgeImageUrl = badgeImageUrl;
        this.criteria = criteria;
        this.pointsValue = pointsValue;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBadgeImageUrl() { return badgeImageUrl; }
    public void setBadgeImageUrl(String badgeImageUrl) { this.badgeImageUrl = badgeImageUrl; }
    public String getCriteria() { return criteria; }
    public void setCriteria(String criteria) { this.criteria = criteria; }
    public int getPointsValue() { return pointsValue; }
    public void setPointsValue(int pointsValue) { this.pointsValue = pointsValue; }
}