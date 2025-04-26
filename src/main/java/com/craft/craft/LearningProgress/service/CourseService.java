package com.craft.craft.LearningProgress.service;

import com.craft.craft.LearningProgress.exception.ResourceNotFoundException;
import com.craft.craft.LearningProgress.model.Course;
import com.craft.craft.LearningProgress.model.Lesson;
import com.craft.craft.LearningProgress.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CourseService {
    
    private final CourseRepository courseRepository;
    
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
    }
    
    public List<Course> getCoursesByCategory(String category) {
        return courseRepository.findByCategory(category);
    }
    
    public List<Course> getCoursesByDifficulty(int difficulty) {
        return courseRepository.findByDifficulty(difficulty);
    }
    
    public List<Course> searchCourses(String query) {
        return courseRepository.findByTitleContainingIgnoreCase(query);
    }
    
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }
    
    public Course updateCourse(String id, Course courseDetails) {
        Course course = getCourseById(id);
        
        course.setTitle(courseDetails.getTitle());
        course.setDescription(courseDetails.getDescription());
        course.setImageUrl(courseDetails.getImageUrl());
        course.setCategory(courseDetails.getCategory());
        course.setDifficulty(courseDetails.getDifficulty());
        
        return courseRepository.save(course);
    }
    
    public void deleteCourse(String id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }
    
    public Course addLessonToCourse(String courseId, Lesson lesson) {
        Course course = getCourseById(courseId);
        course.addLesson(lesson);
        return courseRepository.save(course);
    }
    
    public Course updateLessonInCourse(String courseId, String lessonId, Lesson lessonDetails) {
        Course course = getCourseById(courseId);
        
        List<Lesson> lessons = course.getLessons();
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getId().equals(lessonId)) {
                lessonDetails.setId(lessonId);
                lessons.set(i, lessonDetails);
                break;
            }
        }
        
        course.setLessons(lessons);
        return courseRepository.save(course);
    }
    
    public Lesson getLessonById(String courseId, String lessonId) {
        Course course = getCourseById(courseId);
        
        return course.getLessons().stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));
    }
    
    public List<Lesson> getTotalLessonsInCourse(String courseId) {
        Course course = getCourseById(courseId);
        return course.getLessons();
    }

    public Course removeLesson(String courseId, String lessonId) {
        Course course = getCourseById(courseId);
        List<Lesson> lessons = course.getLessons();
        lessons.removeIf(lesson -> lesson.getId().equals(lessonId));
        course.setLessons(lessons);
        return courseRepository.save(course);
    }
}