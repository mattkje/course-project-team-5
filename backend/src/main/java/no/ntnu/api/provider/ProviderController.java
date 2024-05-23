package no.ntnu.api.provider;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.api.config.AccessUserService;
import no.ntnu.api.course.Course;
import no.ntnu.api.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

  private final ProviderService providerService;
  private final CourseService courseService;
  private final AccessUserService userService;

  @Autowired
  public ProviderController(ProviderService providerService,
                            CourseService courseService, AccessUserService userService) {
    this.providerService = providerService;
    this.courseService = courseService;
    this.userService = userService;
  }

  @GetMapping
  @Operation(summary = "Get all providers", description = "Returns a list of all providers.")
  public List<Provider> getAllProviders() {
    return providerService.getAllProviders();
  }

  @GetMapping("/{id}")
    @Operation(summary = "Get provider by id", description = "Returns the provider that has the matching id as the path variable.")
  public ResponseEntity<Provider> getProviderById(@PathVariable Integer id) {
    return providerService.getProviderById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/{providerId}/courses")
    @Operation(summary = "Get courses by provider", description = "Returns a list of courses that are provided by the provider with the matching id as the path variable.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Courses found"),
    @ApiResponse(responseCode = "404", description = "Courses not found")
  })
  public ResponseEntity<List<Course>> getCoursesByProvider(@PathVariable Long providerId) {
    List<Course> courses = courseService.getCoursesByProviderId(providerId);
    if (courses.isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(courses);
    }
  }

  @GetMapping("/name/{name}")
  @Operation(summary = "Get provider by name", description = "Returns the provider that has the matching name as the path variable.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Provider found"),
    @ApiResponse(responseCode = "404", description = "Provider not found")
  })
  public ResponseEntity<Long> getProviderIdByName(@PathVariable String name) {
    return providerService.getProviderIdByName(name)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Posts a new provider into the API, the provider cannot be null and responds with status.
   *
   * @param courseId The id of the course
   * @param provider The provider to be added into the database
   * @return either a bad request or created status.
   */
  @PostMapping("{courseId}")
  @Operation(summary = "Post provider", description = "Posts a new provider into the API.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Provider created"),
          @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<CourseProvider> postProvider(@PathVariable int courseId, @RequestBody CourseProvider provider) {
    if (provider == null && userService.isAdmin()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } else {
      courseService.postProvider(courseId, provider);
      return ResponseEntity.status(HttpStatus.CREATED).body(provider);
    }
  }

  @GetMapping("/api/courses/{courseId}/providers/{providerId}/price")
  @Operation(summary = "Get course price by provider id and course id", description = "Returns the price of the course that has the matching id as the path variable.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Course price found"),
          @ApiResponse(responseCode = "404", description = "Course price not found")
  })
  public ResponseEntity<Double> getCoursePriceByProviderIdAndCourseId(
      @PathVariable int courseId, @PathVariable int providerId) {
    double price;
    try {
      price = courseService.getCoursePriceByProviderIdAndCourseId(providerId, courseId);
    } catch (Exception e) {
      // Handle the exception based on your application's requirements
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(price);
  }

  @PostMapping
    @Operation(summary = "Post provider", description = "Posts a new provider into the API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Provider created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
  public ResponseEntity<Provider> postProvider(@RequestBody Provider provider) {
    if (provider == null && userService.isAdmin()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } else {
      providerService.addProvider(provider);
      return ResponseEntity.status(HttpStatus.CREATED).body(provider);
    }
  }
}