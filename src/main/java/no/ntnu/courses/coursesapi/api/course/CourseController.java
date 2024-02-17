package no.ntnu.courses.coursesapi.api.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

    /**
     * Returns the course page that has the matching id as the path variable.
     * @param courseId The id of the course
     * @return a Http response either containing the course with matching id or a NOT FOUND response.
     */
    @GetMapping("/courses/{courseId}")
    public ModelAndView getCoursePage(@PathVariable int courseId) {
        ModelAndView modelAndView = new ModelAndView();
        CourseProviders courseProviders = courseService.getCourseWithProviders(courseId);
        if (courseProviders == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        } else {
            modelAndView.addObject("courseProviders", courseProviders);
            modelAndView.setViewName("course");
            return modelAndView;
        }
    }
}
