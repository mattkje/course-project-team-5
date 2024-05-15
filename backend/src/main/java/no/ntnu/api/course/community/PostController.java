package no.ntnu.api.course.community;

import java.util.Collection;

import no.ntnu.api.config.AccessUserService;
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
public class PostController {

    private final PostService postService;
    private final AccessUserService userService;

    @Autowired
    public PostController(PostService courseService, AccessUserService userService) {
        this.postService = courseService;
        this.userService = userService;
    }

    /**
     * Returns the course that has the matching id as the path variable. If the course does not exist the method will
     * return a NOT FOUND response.
     *
     * @param id id representing a course. All id's are different.
     * @return a Http response either containing the course with matching id or a NOT FOUND response.
     */
    @GetMapping("/api/community/courses/{id}")
    public ResponseEntity<Post> getCourse(@PathVariable int id) {
        if(postService.getCourseInfo(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getCourse(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/api/community/courses/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        if(getCourse(id) != null && userService.isAdmin()) {
            postService.deletePost(postService.getCourseInfo(id));
            return ResponseEntity.status(HttpStatus.OK).body("Course deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Returns all the courses in the database.
     * @return a collection of all the courses in the database.
     */

    @GetMapping("/api/community/courses")
    public Collection<Post> getCourses() {
        return postService.getAllCourses();
    }

    /**
     * Creates a new course.
     *
     * @param post The post to create.
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping("/api/community/courses")
    public ResponseEntity<Post> postCourse(@RequestBody Post post) {
        if(post != null && userService.isAdmin()) {
            postService.postCourse(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(post);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
