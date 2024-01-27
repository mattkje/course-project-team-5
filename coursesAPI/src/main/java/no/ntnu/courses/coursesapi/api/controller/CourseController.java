package no.ntnu.courses.coursesapi.api.controller;

import no.ntnu.courses.coursesapi.api.model.Course;
import no.ntnu.courses.coursesapi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/course")
    public Course getCourse(@RequestParam Integer id) {
        return courseService.getCourse(id); //Create an Exception
    }
}
