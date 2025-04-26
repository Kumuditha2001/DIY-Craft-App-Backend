package com.craft.craft.LearningProgress.service;

import com.craft.craft.LearningProgress.dto.response.CourseProgressResponse;
import com.craft.craft.LearningProgress.dto.response.LearningStatsResponse;
import com.craft.craft.LearningProgress.exception.ResourceNotFoundException;
import com.craft.craft.LearningProgress.model.Course;
import com.craft.craft.LearningProgress.model.LearningProgress;
import com.craft.craft.LearningProgress.repository.CourseRepository;
import com.craft.craft.LearningProgress.repository.LearningProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LearningProgressService {
    
    private final LearningProgressRepository progressRepository;
    private final CourseRepository courseRepository;
    
    @Autowired
    public LearningProgressService(LearningProgressRepository progressRepository, 
                                 CourseRepository courseRepository) {
        this.progressRepository = progressRepository;
        this.courseRepository = courseRepository;
    }
    
    public LearningProgress recordLessonStart(String userId, String courseId, String lessonId) {
        LearningProgress progress = progressRepository
            .findByUserIdAndLessonId(userId, lessonId)
            .orElse(new LearningProgress(userId, courseId, lessonId));
        
        if (!progress.isCompleted()) {
            progress.setStartDate(LocalDateTime.now());
        }
        
        return progressRepository.save(progress);
    }
    
    public LearningProgress completeLessonProgress(String userId, String lessonId, int quizScore) {
        LearningProgress progress = progressRepository
            .findByUserIdAndLessonId(userId, lessonId)
            .orElseThrow(() -> new ResourceNotFoundException("Progress", "lessonId", lessonId));
        
        progress.setCompleted(true);
        progress.setCompletionDate(LocalDateTime.now());
        progress.setQuizScore(quizScore);
        
        if (progress.getStartDate() != null) {
            long seconds = ChronoUnit.SECONDS.between(
                progress.getStartDate(), progress.getCompletionDate());
            progress.setTimeSpentSeconds(seconds);
        }
        
        checkAndAwardAchievements(progress);
        
        return progressRepository.save(progress);
    }
    
    public LearningProgress updateLessonNotes(String userId, String lessonId, String notes) {
        LearningProgress progress = progressRepository
            .findByUserIdAndLessonId(userId, lessonId)
            .orElseThrow(() -> new ResourceNotFoundException("Progress", "lessonId", lessonId));
        
        progress.setNotes(notes);
        return progressRepository.save(progress);
    }
    
    public CourseProgressResponse getCourseProgress(String userId, String courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
        
        long totalLessons = course.getLessons().size();
        
        if (totalLessons == 0) {
            return new CourseProgressResponse(courseId, 0, 0, 0);
        }
        
        long completedLessons = progressRepository
            .countByUserIdAndCourseIdAndCompleted(userId, courseId, true);
        
        double percentage = (double) completedLessons / totalLessons * 100;
        
        return new CourseProgressResponse(
            courseId,
            completedLessons,
            totalLessons,
            Math.round(percentage * 100.0) / 100.0
        );
    }
    
    public LearningStatsResponse getUserLearningStats(String userId) {
        List<LearningProgress> allProgress = progressRepository.findByUserId(userId);
        
        if (allProgress.isEmpty()) {
            return new LearningStatsResponse(0, 0, 0);
        }
        
        long completedLessons = allProgress.stream()
            .filter(LearningProgress::isCompleted)
            .count();
            
        double avgQuizScore = allProgress.stream()
            .filter(LearningProgress::isCompleted)
            .mapToInt(LearningProgress::getQuizScore)
            .average()
            .orElse(0);
            
        long totalTimeSpent = allProgress.stream()
            .mapToLong(LearningProgress::getTimeSpentSeconds)
            .sum();
        
        LearningStatsResponse stats = new LearningStatsResponse(
            completedLessons,
            Math.round(avgQuizScore * 100.0) / 100.0,
            totalTimeSpent
        );
        
        int achievementsCount = allProgress.stream()
            .flatMap(p -> p.getAchievementIds().stream())
            .collect(Collectors.toSet())
            .size();
        stats.setAchievementsEarned(achievementsCount);
        
        Map<String, Integer> courseProgress = new HashMap<>();
        
        List<String> courseIds = allProgress.stream()
            .map(LearningProgress::getCourseId)
            .distinct()
            .collect(Collectors.toList());
        
        for (String courseId : courseIds) {
            CourseProgressResponse progress = getCourseProgress(userId, courseId);
            courseProgress.put(courseId, (int) Math.round(progress.getPercentageComplete()));
        }
        
        stats.setCoursesProgress(courseProgress);
        return stats;
    }
    
    private void checkAndAwardAchievements(LearningProgress progress) {
        long completedLessons = progressRepository.countByUserIdAndCompleted(
            progress.getUserId(), true);
        
        if (completedLessons == 1) {
            progress.getAchievementIds().add("FIRST_LESSON_COMPLETE");
        }
        
        if (progress.getQuizScore() == 100) {
            progress.getAchievementIds().add("PERFECT_SCORE");
        }
        
        Course course = courseRepository.findById(progress.getCourseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", "id", progress.getCourseId()));
        
        long completedLessonsInCourse = progressRepository
            .countByUserIdAndCourseIdAndCompleted(
                progress.getUserId(), progress.getCourseId(), true);
                
        if (completedLessonsInCourse == course.getLessons().size()) {
            progress.getAchievementIds().add("COURSE_COMPLETED");
        }
        
        if (progress.getTimeSpentSeconds() < 300) {
            progress.getAchievementIds().add("SPEED_LEARNER");
        }
    }
    
    public LearningProgress getProgressByUserAndLesson(String userId, String lessonId) {
        return progressRepository.findByUserIdAndLessonId(userId, lessonId)
            .orElseThrow(() -> new ResourceNotFoundException("Progress", "userId/lessonId", userId + "/" + lessonId));
    }
    
    public List<LearningProgress> getProgressByUserId(String userId) {
        return progressRepository.findByUserId(userId);
    }
    
    public List<LearningProgress> getProgressByUserAndCourse(String userId, String courseId) {
        return progressRepository.findByUserIdAndCourseId(userId, courseId);
    }
    
    public List<LearningProgress> getCompletedLessonsByUser(String userId) {
        return progressRepository.findAllCompletedByUser(userId);
    }
    
    public void deleteProgress(String progressId) {
        if (!progressRepository.existsById(progressId)) {
            throw new ResourceNotFoundException("Progress", "id", progressId);
        }
        progressRepository.deleteById(progressId);
    }
    
    public void resetLessonProgress(String userId, String lessonId) {
        LearningProgress progress = progressRepository
            .findByUserIdAndLessonId(userId, lessonId)
            .orElseThrow(() -> new ResourceNotFoundException("Progress", "lessonId", lessonId));
            
        progress.setCompleted(false);
        progress.setCompletionDate(null);
        progress.setQuizScore(0);
        progress.setTimeSpentSeconds(0);
        progressRepository.save(progress);
    }
    
    public int getUserLearningStreak(String userId) {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime yesterday = today.minusDays(1);
        
        // Check for activity today
        boolean hasActivityToday = !progressRepository
            .findByUserIdAndCompletionDateAfter(userId, today).isEmpty();
            
        // Check for activity yesterday
        boolean hasActivityYesterday = !progressRepository
            .findByUserIdAndCompletionDateAfter(userId, yesterday).isEmpty();
        
        // If no activity today or yesterday, streak is 0
        if (!hasActivityToday && !hasActivityYesterday) {
            return 0;
        }
        
        // Start counting streak from today or yesterday
        LocalDateTime currentDay = hasActivityToday ? today : yesterday;
        int streak = hasActivityToday ? 1 : 0;
        
        // Count backwards to find consecutive days with activity
        while (true) {
            currentDay = currentDay.minusDays(1);
            LocalDateTime dayStart = currentDay.toLocalDate().atStartOfDay();
            LocalDateTime dayEnd = dayStart.plusDays(1);
            
            // Check if there was activity on this day
            List<LearningProgress> activities = progressRepository
                .findByUserIdAndCompletionDateAfter(userId, dayStart);
            
            boolean hasActivity = activities.stream()
                .anyMatch(p -> p.getCompletionDate() != null && 
                             p.getCompletionDate().isBefore(dayEnd));
                
            if (hasActivity) {
                streak++;
            } else {
                break;
            }
        }
        
        return streak;
    }
    
    public Map<String, Object> getUserEngagementMetrics(String userId) {
        List<LearningProgress> allProgress = progressRepository.findByUserId(userId);
        Map<String, Object> metrics = new HashMap<>();
        
        if (allProgress.isEmpty()) {
            metrics.put("totalLessonsViewed", 0);
            metrics.put("completionRate", 0);
            metrics.put("averageCompletionTime", 0);
            metrics.put("learningStreak", 0);
            return metrics;
        }
        
        long totalLessons = allProgress.size();
        long completedLessons = allProgress.stream()
            .filter(LearningProgress::isCompleted)
            .count();
            
        double completionRate = totalLessons > 0 ? 
            (double) completedLessons / totalLessons * 100 : 0;
            
        double avgCompletionTime = allProgress.stream()
            .filter(p -> p.getTimeSpentSeconds() > 0)
            .mapToLong(LearningProgress::getTimeSpentSeconds)
            .average()
            .orElse(0);
            
        int learningStreak = getUserLearningStreak(userId);
        
        metrics.put("totalLessonsViewed", totalLessons);
        metrics.put("completionRate", Math.round(completionRate * 100.0) / 100.0);
        metrics.put("averageCompletionTime", Math.round(avgCompletionTime * 100.0) / 100.0);
        metrics.put("learningStreak", learningStreak);
        
        return metrics;
    }
}