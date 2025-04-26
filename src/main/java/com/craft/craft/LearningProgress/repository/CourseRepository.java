package com.craft.craft.LearningProgress.repository;

import com.craft.craft.LearningProgress.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    List<Course> findByCategory(String category);
    List<Course> findByDifficulty(int difficulty);
    List<Course> findByTitleContainingIgnoreCase(String title);
}