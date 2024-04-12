package no.ntnu.api.course;

import java.util.Objects;

import no.ntnu.api.provider.CourseProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<CourseWithProvidersAndKeywords> getCourse(@PathVariable int id) {
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
    public Collection<CourseWithProvidersAndKeywords> getCourses() {
        return courseService.getAllCourses();
    }

    /**
     * Returns the course that has the matching id as the path variable.
     * @param courseId The id of the course
     * @param providerId The id of the provider
     * @param keywordId The id of the keyword
     * @return a Http response either containing the course with matching id or a NOT FOUND response.
     */
    @GetMapping("/api/courses/{courseId}/{providerId}/{keywordId}")
    public CourseWithProvidersAndKeywords getCourseFromProvider(@PathVariable int courseId,
                                                                @PathVariable int providerId,
                                                                @PathVariable int keywordId) {
        if (courseService.getCourse(courseId, providerId, keywordId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        } else {
            return courseService.getCourse(courseId, providerId, keywordId);
        }
    }

    /**
     * Deletes a course from the api, the course is chosen by the given id parameter.
     * @param id the id of the course to be deleted
     * @return returns a Not found response if the course is not found. Or returns an OK response
     *         if the course is found and deleted
     */
    @DeleteMapping("/api/courses/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        if(getCourse(id) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        else{
            courseService.deleteCourse(Objects.requireNonNull(getCourse(id).getBody()).course());
            return ResponseEntity.status(HttpStatus.OK).body("Course deleted");
        }
    }

    /**
     * Posts a new course into the API, the course cannot be null and responds with status
     * Bad request if it is. If it is not null it responds with status Created.
     * @param course The course to be added into the database
     * @return either a bad request or created status.
     */
    @PostMapping("/api/courses")
    public ResponseEntity<Course> postCourse(@RequestBody Course course) {
        if(course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            courseService.postCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        }
    }

    /**
     * Posts a new provider into the API, the provider cannot be null and responds with status
     * @param courseId The id of the course
     * @param provider The provider to be added into the database
     * @return either a bad request or created status.
     */
    @PostMapping("/api/courses/{courseId}/providers")
    public ResponseEntity<CourseProvider> postProvider(@PathVariable int courseId, @RequestBody CourseProvider provider) {
        if(provider == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            courseService.postProvider(courseId, provider);
            return ResponseEntity.status(HttpStatus.CREATED).body(provider);
        }
    }

    /**
     * Deletes a provider from the api, the provider is chosen by the given id parameter.
     * @param courseId The id of the course
     * @param providerId The id of the provider
     * @param keywordId The id of the keyword
     * @return returns a Not found response if the provider is not found. Or returns an OK response
     */
    @DeleteMapping("/api/courses/{courseId}/providers/{providerId}/keywords/{keywordId}")
    public ResponseEntity<String> deleteProvider(@PathVariable int courseId, @PathVariable int providerId,
                                                 @PathVariable int keywordId) {
        if(courseService.getCourse(courseId, providerId, keywordId) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            courseService.deleteProvider(courseId, providerId);
            return ResponseEntity.status(HttpStatus.OK).body("Provider deleted");
        }
    }
}
