package no.ntnu.courses.coursesapi.api.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/api/courses/{id}")
    public Course getCourse(@PathVariable int id) {
        return courseService.getCourseInfo(id); //Create an Exception
    }

    @GetMapping("/api/courses")
    public Collection<Course> getCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/api/courses/{courseId}/{providerId}")
    public CourseProviders getCourseFromProvider(@PathVariable int courseId, @PathVariable int providerId) {
        return courseService.getCourse(courseId, providerId); //Create an Exception
    }

    @PostMapping("/api/courses")
    public ResponseEntity<String> postCourse(@RequestParam Course course) {
        Iterator<Course> it = courseService.getAllCourses().iterator();
        Course course1 = it.next();
        while (it.hasNext()) {
            if(course.getCourseId() == course1.getCourseId()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Courses can not have the same ID");
            }
        }
        courseService.addCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body("Course successfully created");
    }
}
