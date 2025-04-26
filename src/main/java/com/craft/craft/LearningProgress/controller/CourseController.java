package com.craft.craft.LearningProgress.controller;

import com.craft.craft.LearningProgress.model.Course;
import com.craft.craft.LearningProgress.model.Lesson;
import com.craft.craft.LearningProgress.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;
    
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        Course course = courseService.getCourseById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Course>> getCoursesByCategory(@PathVariable String category) {
        List<Course> courses = courseService.getCoursesByCategory(category);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    
    @GetMapping("/difficulty/{level}")
    public ResponseEntity<List<Course>> getCoursesByDifficulty(@PathVariable int level) {
        List<Course> courses = courseService.getCoursesByDifficulty(level);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam String query) {
        List<Course> courses = courseService.searchCourses(query);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        Course newCourse = courseService.createCourse(course);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable String id, 
            @Valid @RequestBody Course courseDetails) {
        Course updatedCourse = courseService.updateCourse(id, courseDetails);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<Course> addLessonToCourse(
            @PathVariable String courseId,
            @Valid @RequestBody Lesson lesson) {
        Course updatedCourse = courseService.addLessonToCourse(courseId, lesson);
        return new ResponseEntity<>(updatedCourse, HttpStatus.CREATED);
    }
    
    @PutMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<Course> updateLessonInCourse(
            @PathVariable String courseId,
            @PathVariable String lessonId,
            @Valid @RequestBody Lesson lessonDetails) {
        Course updatedCourse = courseService.updateLessonInCourse(courseId, lessonId, lessonDetails);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }
    
    @GetMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<Lesson> getLessonById(
            @PathVariable String courseId,
            @PathVariable String lessonId) {
        Lesson lesson = courseService.getLessonById(courseId, lessonId);
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }
    
    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<Lesson>> getAllLessonsInCourse(@PathVariable String courseId) {
        List<Lesson> lessons = courseService.getTotalLessonsInCourse(courseId);
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }
    
    @DeleteMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<Course> deleteLesson(
            @PathVariable String courseId,
            @PathVariable String lessonId) {
        Course updatedCourse = courseService.removeLesson(courseId, lessonId);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }
}