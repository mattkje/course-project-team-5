package no.ntnu.api.course.community;

import java.util.Collection;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityCourseController {

    private final CommunityCourseService communityCourseService;

    @Autowired
    public CommunityCourseController(CommunityCourseService courseService) {
        this.communityCourseService = courseService;
    }

    /**
     * Returns the course that has the matching id as the path variable. If the course does not exist the method will
     * return a NOT FOUND response.
     *
     * @param id id representing a course. All id's are different.
     * @return a Http response either containing the course with matching id or a NOT FOUND response.
     */
    @GetMapping("/api/community/courses/{id}")
    public ResponseEntity<CommunityCourse> getCourse(@PathVariable int id) {
        if(communityCourseService.getCourseInfo(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(communityCourseService.getCourse(id));
        }
    }

    /**
     * Returns all the courses in the database.
     * @return a collection of all the courses in the database.
     */

    @GetMapping("/api/community/courses")
    public Collection<CommunityCourse> getCourses() {
        return communityCourseService.getAllCourses();
    }

}
