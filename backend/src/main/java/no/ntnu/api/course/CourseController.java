package no.ntnu.api.course;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import java.util.*;

import no.ntnu.api.config.AccessUserService;
import no.ntnu.api.keywords.CourseKeywords;
import no.ntnu.api.keywords.Keywords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {

    private final CourseService courseService;
    private final AccessUserService userService;

    @Autowired
    public CourseController(CourseService courseService, AccessUserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    /**
     * Returns the course that has the matching id as the path variable. If the course does not exist the method will
     * return a NOT FOUND response.
     *
     * @param id id representing a course. All id's are different.
     * @return a Http response either containing the course with matching id or a NOT FOUND response.
     */

    @Schema(description = "Returns the course that has the matching id as the path variable.")
    @GetMapping("/api/courses/{id}")
    public ResponseEntity<CourseWithProvidersAndKeywords> getCourse(@PathVariable int id) {
        if(courseService.getCourseInfo(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseWithProviders(id));
        }
    }

    /**
     * Returns all the courses in the database with provider and keywords.
     * @return a collection of all the courses in the database.
     */
    @Schema(description = "Returns all the courses in the database.")
    @GetMapping("/api/courses")
    public Collection<CourseWithProvidersAndKeywords> getCourses() {
        if(userService.isAdmin()) {
            return courseService.getAllCourses();
        } else {
            ArrayList<CourseWithProvidersAndKeywords> courses = new ArrayList<>();
            for (CourseWithProvidersAndKeywords course : courseService.getAllCourses()) {
                if(course.course().isActive()) {
                    courses.add(course);
                }
            }
            return courses;
        }
    }

    @Schema(description = "Set a keyword for a course")
    @PostMapping("/api/courses/keyword")
    public ResponseEntity<?> postKeyword(@RequestBody Keywords keyword) {
        if(userService.isAdmin() && keyword != null) {
            courseService.postKeyword(keyword);
            return ResponseEntity.status(HttpStatus.CREATED).body(keyword);
        } else {
            return null;
        }
    }

    @Schema(description = "Set a keyword for a course")
    @PostMapping("/api/courses/keywords/{courseId}")
    public ResponseEntity<?> postKeywordToCourse(@PathVariable int courseId, @RequestBody Set<Integer> keywordIds) {
        if(userService.isAdmin()) {
            courseService.postKeywordsToCourse(courseId, keywordIds);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return null;
        }
    }

    /**
     * Deletes a course from the api, the course is chosen by the given id parameter.
     * @param id the id of the course to be deleted
     * @return returns a Not found response if the course is not found. Or returns an OK response
     *         if the course is found and deleted
     */
    @Schema(description = "Deletes a course from the api, the course is chosen by the given id parameter.")
    @DeleteMapping("/api/courses/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        if(getCourse(id) != null && userService.isAdmin()) {
            courseService.deleteCourse(courseService.getCourseInfo(id));
            return ResponseEntity.status(HttpStatus.OK).body("Course deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Posts a new course into the API, the course cannot be null and responds with status
     * Bad request if it is. If it is not null it responds with status Created.
     * @param course The course to be added into the database
     * @return either a bad request or created status.
     */
    @Schema(description = "Posts a new course into the API.")
    @PostMapping("/api/courses")
    public ResponseEntity<Course> postCourse(@RequestBody Course course) {
        if(course != null && userService.isAdmin()) {
            courseService.postCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Schema(description = "gets all courses given a category.")
    @GetMapping("/api/courses/category/{category}")
    public Collection<Course> getCoursesByCategory(@PathVariable String category) {
        return courseService.getCoursesByCategory(category);
    }

    @Schema(description = "gets all courses given a level.")
    @GetMapping("/api/courses/level/{level}")
    public Collection<Course> getCoursesByLevel(@PathVariable String level) {
        return courseService.getCoursesByLevel(level);
    }

    @Schema(description = "gets all courses given a course size.")
    @GetMapping("/api/courses/course_size/{size}")
    public Collection<Course> getCoursesBySize(@PathVariable double size) {
        return courseService.getCoursesBySize(size);
    }

    @Schema(description = "gets all courses with their lowest provider price.")
    @GetMapping("/api/courses/lowest-price")
    public Map<String, Double> getLowestPrice() {
        return courseService.getLowestPrice();
    }


    @Schema(description = "gets a course by it's id.")
    @GetMapping("/api/courses/ids")
    public Collection<Course> getCoursesByIds(@RequestParam List<Integer> ids) {
        return courseService.getCoursesByIds(ids);
    }

    @Schema(description = "gets all courses by a single provider based on the name.")
    @GetMapping("/api/courses/provider/{providerName}")
    public Collection<Course> getCoursesByProvider(@PathVariable String providerName) {
        return courseService.getCoursesByProvider(providerName);
    }

    @Schema(description = "gets all courses within a date range.")
    @GetMapping("/api/courses/date-range")
    public Collection<Course> getCoursesWithinDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        return courseService.getCoursesWithinDateRange(startDate, endDate);
    }

    @Schema(description = "gets a courses active status (true or false).")
    @PutMapping("/api/courses/active/{id}")
    public ResponseEntity<?> changeActiveCourse(@PathVariable int id) {
        if(courseService.getCourseInfo(id) == null && !userService.isAdmin()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            courseService.changeActiveCourse(id);
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseInfo(id));
        }
    }

    @Schema(description = "gets all keywords.")
    @GetMapping("/api/keywords")
    public Collection<Keywords> getKeywords() {
        return courseService.getAllKeywords();
    }

    @Schema(description = "gets the latest course")
    @GetMapping("/api/courses/newest")
    public ResponseEntity<?> getNewestCourse() {
        Course newestCourse = courseService.getNewestCourse();
        if(userService.isAdmin()) {
            return ResponseEntity.status(HttpStatus.OK).body(newestCourse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Schema(description = "gets all courses with their corresponding keywords.")
    @GetMapping("/api/courses/keywords")
    public ResponseEntity<?> getKeywordsofCourses() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getKeywordsOfCourse());
    }
}
