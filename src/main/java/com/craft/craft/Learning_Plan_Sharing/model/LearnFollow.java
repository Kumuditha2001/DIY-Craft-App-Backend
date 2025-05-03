package com.craft.craft.Learning_Plan_Sharing.model;


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
@Document(collection = "learnFollow")
public class LearnFollow {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String planId;
    private String planTitle;
    private String userId;
    private String name;
    private String email;
    private String watchedDuration ;
    private String isCompleted ;
    private String progress ;
}
