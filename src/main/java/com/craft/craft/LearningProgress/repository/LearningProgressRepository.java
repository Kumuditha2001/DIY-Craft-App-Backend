package com.craft.craft.LearningProgress.repository;

import com.craft.craft.LearningProgress.model.LearningProgress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LearningProgressRepository extends MongoRepository<LearningProgress, String> {
    List<LearningProgress> findByUserId(String userId);
    
    List<LearningProgress> findByUserIdAndCourseId(String userId, String courseId);
    
    Optional<LearningProgress> findByUserIdAndLessonId(String userId, String lessonId);
    
    @Query("{ 'userId': ?0, 'courseId': ?1, 'completed': true }")
    List<LearningProgress> findCompletedLessonsByCourse(String userId, String courseId);
    
    long countByUserIdAndCourseIdAndCompleted(String userId, String courseId, boolean completed);
    
    // Add the missing count method
    long countByUserIdAndCompleted(String userId, boolean completed);
    
    @Query("{ 'userId': ?0, 'completed': true }")
    List<LearningProgress> findAllCompletedByUser(String userId);
    
    @Query(value = "{ 'courseId': ?0 }", count = true)
    long countByCourseId(String courseId);
    
    @Query("{ 'userId': ?0, 'quizScore': { $gte: ?1 } }")
    List<LearningProgress> findLessonsWithMinimumScore(String userId, int minScore);
    
    // Add the missing query method for completion dates
    @Query("{ 'userId': ?0, 'completionDate': { $gte: ?1 } }")
    List<LearningProgress> findByUserIdAndCompletionDateAfter(String userId, LocalDateTime date);
}