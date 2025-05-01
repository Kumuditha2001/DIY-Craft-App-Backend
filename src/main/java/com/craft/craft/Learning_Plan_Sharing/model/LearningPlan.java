package com.craft.craft.Learning_Plan_Sharing.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "learningPlan")
public class LearningPlan {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String userId; 
    private String profilPic;
    private String headline;   
    private String fullname;   
    private String title;    
    private String description; 
    private String timeLine;         
    private String thumbnail;
    private String resources;  
    private Date createdAt;
}
