package no.ntnu.api.course;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.Id;
import java.util.*;
import no.ntnu.api.config.AccessUserService;
import no.ntnu.api.keywords.CourseKeywords;
import no.ntnu.api.keywords.Keywords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the course entity.
 * This class handles all the requests that are related to the course entity.
 */
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
    @Operation(summary = "Get one course.",
            description = "Returns the course that has the matching id as the path variable. If the course does not exist the method will return a NOT FOUND response.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course found."),
            @ApiResponse(responseCode = "404", description = "Course not found.", content = @Content)
    })

    public ResponseEntity<CourseWithProvidersAndKeywords> getCourse(@PathVariable int id) {
        if (courseService.getCourseInfo(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseWithProviders(id));
        }
    }



    /**
     * Returns all the courses in the database with provider and keywords.
     * @return a collection of all the courses in the database.
     */

    @GetMapping("/api/courses")
    @Operation(summary = "Get all courses.",
    description = "Returns all the courses in the database with provider and keywords.")
    public Collection<CourseWithProvidersAndKeywords> getCourses() {
        if (userService.isAdmin()) {
            return courseService.getAllCourses();
        } else {
            ArrayList<CourseWithProvidersAndKeywords> courses = new ArrayList<>();
            for (CourseWithProvidersAndKeywords course : courseService.getAllCourses()) {
                if (course.course().isActive()) {
                    courses.add(course);
                }
            }
            return courses;
        }
    }

    @PostMapping("/api/courses/keyword")
    @Operation(summary = "Post a keyword.", description = "Posts a keyword into the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Keyword created."),
        @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content)
    })
    public ResponseEntity<?> postKeyword(@RequestBody Keywords keyword) {
        if(userService.isAdmin() && keyword != null) {
            courseService.postKeyword(keyword);
            return ResponseEntity.status(HttpStatus.CREATED).body(keyword);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/api/courses/keywords/{courseId}")
    @Operation(summary = "Post a keyword to a course.", description = "Posts a keyword to a course.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Keyword posted to course."),
        @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content)
    })
    public ResponseEntity<?> postKeywordToCourse(@PathVariable int courseId, @RequestBody Set<Integer> keywordIds) {
        if (userService.isAdmin()) {
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
    @Operation(summary = "Delete a course.",
    description = "Deletes the course that has the matching id as the path variable.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course deleted."),
        @ApiResponse(responseCode = "404", description = "Course not found.", content = @Content)
    })
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        if (getCourse(id) != null && userService.isAdmin()) {
            courseService.deleteCourse(courseService.getCourseInfo(id));
            return ResponseEntity.status(HttpStatus.OK).body("Course deleted");
        } else {
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
    @Operation(summary = "Post a course.",
    description = "Posts a new course into the API, the course cannot be null.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Course created."),
        @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content)
    })
    public ResponseEntity<Course> postCourse(@RequestBody Course course) {
        if(course != null && userService.isAdmin()) {
            courseService.postCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/api/courses/category/{category}")
    @Operation(summary = "Get courses by category.",
    description = "Gets all courses given a category.")
    public Collection<Course> getCoursesByCategory(@PathVariable String category) {
        return courseService.getCoursesByCategory(category);
    }

    @GetMapping("/api/courses/level/{level}")
    @Operation(summary = "Get courses by level.",
    description = "Gets all courses given a course level.")
    public Collection<Course> getCoursesByLevel(@PathVariable String level) {
        return courseService.getCoursesByLevel(level);
    }

    @GetMapping("/api/courses/course_size/{size}")
    @Operation(summary = "Get courses by size.",
    description = "Gets all courses given a course size.")
    public Collection<Course> getCoursesBySize(@PathVariable double size) {
        return courseService.getCoursesBySize(size);
    }

    @GetMapping("/api/courses/lowest-price")
    @Operation(summary = "Get the lowest price.",
    description = "Gets the lowest price of all courses.")
    public Map<String, Double> getLowestPrice() {
        return courseService.getLowestPrice();
    }

    @GetMapping("/api/courses/ids")
    @Operation(summary = "Get courses by ids.",
    description = "Gets all courses by a list of ids.")
    public Collection<Course> getCoursesByIds(@RequestParam List<Integer> ids) {
        return courseService.getCoursesByIds(ids);
    }

    @GetMapping("/api/courses/provider/{providerName}")
    @Operation(summary = "Get courses by provider.",
    description = "Gets all courses given a provider name.")
    public Collection<Course> getCoursesByProvider(@PathVariable String providerName) {
        return courseService.getCoursesByProvider(providerName);
    }

    @GetMapping("/api/courses/date-range")
    @Operation(summary = "Get courses within date range.",
    description = "Gets all courses within a given date range.")
    public Collection<Course> getCoursesWithinDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        return courseService.getCoursesWithinDateRange(startDate, endDate);
    }

    @PutMapping("/api/courses/active/{id}")
    @Operation(summary = "Change active course.",
    description = "Changes the active status of a course.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course active status changed."),
        @ApiResponse(responseCode = "404", description = "Course not found.", content = @Content)
    })
    public ResponseEntity<?> changeActiveCourse(@PathVariable int id) {
        if(courseService.getCourseInfo(id) == null && !userService.isAdmin()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            courseService.changeActiveCourse(id);
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseInfo(id));
        }
    }

    @GetMapping("/api/keywords")
    @Operation(summary = "Get all keywords.",
    description = "Gets all keywords in the database.")
    public Collection<Keywords> getKeywords() {
        return courseService.getAllKeywords();
    }

    @GetMapping("/api/courses/newest")
    @Operation(summary = "Get newest course.",
    description = "Gets the newest course in the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Newest course found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content)
    })
    public ResponseEntity<?> getNewestCourse() {
        Course newestCourse = courseService.getNewestCourse();
        if(userService.isAdmin()) {
            return ResponseEntity.status(HttpStatus.OK).body(newestCourse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/api/courses/keywords")
    @Operation(summary = "Get keywords of courses.",
    description = "Gets all keywords of courses.")
    @ApiResponse(responseCode = "200", description = "Keywords found.")
    public ResponseEntity<?> getKeywordsofCourses() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getKeywordsOfCourse());
    }
}
