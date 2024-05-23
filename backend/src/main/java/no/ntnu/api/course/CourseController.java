package no.ntnu.api.course;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @GetMapping("/api/courses/{id}")
    @Schema(description = "Returns the course that has the matching id as the path variable.")
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
    @Schema(description = "Returns all the courses in the database.")
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

    @PostMapping("/api/courses/keyword")
    @Schema(description = "Adds a keyword to database")
    public ResponseEntity<?> postKeyword(@RequestBody Keywords keyword) {
        if(userService.isAdmin() && keyword != null) {
            courseService.postKeyword(keyword);
            return ResponseEntity.status(HttpStatus.CREATED).body(keyword);
        } else {
            return null;
        }
    }

    @PostMapping("/api/courses/keywords/{courseId}")
    @Schema(description = "Adds a keyword to course")
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
    @DeleteMapping("/api/courses/{id}")
    @Schema(description = "Deletes course from database")
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
    @PostMapping("/api/courses")
    @Schema(description = "Adds a course to database")
    public ResponseEntity<Course> postCourse(@RequestBody Course course) {
        if(course != null && userService.isAdmin()) {
            courseService.postCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/api/courses/category/{category}")
    @Schema(description = "Returns all courses by category")
    public Collection<Course> getCoursesByCategory(@PathVariable String category) {
        return courseService.getCoursesByCategory(category);
    }

    @GetMapping("/api/courses/level/{level}")
    @Schema(description = "Returns all courses by level")
    public Collection<Course> getCoursesByLevel(@PathVariable String level) {
        return courseService.getCoursesByLevel(level);
    }

    @GetMapping("/api/courses/course_size/{size}")
    @Schema(description = "Returns all courses by size")
    public Collection<Course> getCoursesBySize(@PathVariable double size) {
        return courseService.getCoursesBySize(size);
    }

    @GetMapping("/api/courses/lowest-price")
    @Schema(description = "Returns all courses by lowest size")
    public Map<String, Double> getLowestPrice() {
        return courseService.getLowestPrice();
    }


    @GetMapping("/api/courses/ids")
    @Schema(description = "Returns all courses by ids")
    public Collection<Course> getCoursesByIds(@RequestParam List<Integer> ids) {
        return courseService.getCoursesByIds(ids);
    }

    @GetMapping("/api/courses/provider/{providerName}")
    @Schema(description = "Returns all courses by provider names")
    public Collection<Course> getCoursesByProvider(@PathVariable String providerName) {
        return courseService.getCoursesByProvider(providerName);
    }

    @GetMapping("/api/courses/date-range")
    @Schema(description = "Returns all courses within date range")
    public Collection<Course> getCoursesWithinDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        return courseService.getCoursesWithinDateRange(startDate, endDate);
    }

    @PutMapping("/api/courses/active/{id}")
    @Schema(description = "Returns all active courses by id")
    public ResponseEntity<?> changeActiveCourse(@PathVariable int id) {
        if(courseService.getCourseInfo(id) == null && !userService.isAdmin()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            courseService.changeActiveCourse(id);
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseInfo(id));
        }
    }

    @GetMapping("/api/keywords")
    @Schema(description = "Returns all keywords")
    public Collection<Keywords> getKeywords() {
        return courseService.getAllKeywords();
    }

    @GetMapping("/api/courses/newest")
    @Schema(description = "Returns newest")
    public ResponseEntity<?> getNewestCourse() {
        Course newestCourse = courseService.getNewestCourse();
        if(userService.isAdmin()) {
            return ResponseEntity.status(HttpStatus.OK).body(newestCourse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/api/courses/keywords")
    @Schema(description = "Returns all keywords of courses")
    public ResponseEntity<?> getKeywordsofCourses() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getKeywordsOfCourse());
    }
}
