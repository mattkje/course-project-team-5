package no.ntnu.courses.coursesapi.api.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Returns the course that has the matching id as the path variable. If the course does not exist the method will
     * return a NOT FOUND response.
     *
     * @param id id representing a course. All id's are different.
     * @return a Http response either containing the course with matching id or a NOT FOUND response.
     */
    @GetMapping("/api/courses/{id}")
    public ResponseEntity<CourseProviders> getCourse(@PathVariable int id) {
        if(courseService.getCourseInfo(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseWithProviders(id));
        }
    }

    /**
     * Returns all the courses in the database.
     * @return a collection of all the courses in the database.
     */
    @GetMapping("/api/courses")
    public Collection<CourseProviders> getCourses() {
        return courseService.getAllCourses();
    }

    /**
     * Returns the course that has the matching id as the path variable.
     * @param courseId The id of the course
     * @param providerId The id of the provider
     * @return a Http response either containing the course with matching id or a NOT FOUND response.
     */
    @GetMapping("/api/courses/{courseId}/{providerId}")
    public CourseProviders getCourseFromProvider(@PathVariable int courseId, @PathVariable int providerId) {
        if (courseService.getCourse(courseId, providerId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        } else {
            return courseService.getCourse(courseId, providerId);
        }
    }

}
