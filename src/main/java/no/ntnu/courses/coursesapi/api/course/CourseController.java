package no.ntnu.courses.coursesapi.api.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/api/courses/{id}")
    public Course getCourse(@PathVariable int id) {
        return courseService.getCourse(id); //Create an Exception
    }

    @GetMapping("/api/courses")
    public Collection<Course> getCourses() {
        return courseService.getAllCourses();
    }

}
