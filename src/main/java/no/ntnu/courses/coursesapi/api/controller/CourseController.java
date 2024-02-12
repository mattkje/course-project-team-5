package no.ntnu.courses.coursesapi.api.controller;

import no.ntnu.courses.coursesapi.api.model.Course;
import no.ntnu.courses.coursesapi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses/{id}")
    public Course getCourse(@RequestParam int id) {
        return courseService.getCourse(id); //Create an Exception
    }

    @GetMapping("/courses")
    public Collection<Course> getCourses() {
        return courseService.getAllCourses();
    }

}
