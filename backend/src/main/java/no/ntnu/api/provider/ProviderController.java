package no.ntnu.api.provider;

import no.ntnu.api.config.AccessUserService;
import no.ntnu.api.course.Course;
import no.ntnu.api.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

  private final ProviderService providerService;
  private final CourseService courseService;
  private final AccessUserService userService;

  @Autowired
  public ProviderController(ProviderService providerService, CourseService courseService, AccessUserService userService) {
    this.providerService = providerService;
    this.courseService = courseService;
    this.userService = userService;
  }

  @GetMapping
  public List<Provider> getAllProviders() {
    return providerService.getAllProviders();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Provider> getProviderById(@PathVariable Integer id) {
    return providerService.getProviderById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/{providerId}/courses")
  public ResponseEntity<List<Course>> getCoursesByProvider(@PathVariable Long providerId) {
    List<Course> courses = courseService.getCoursesByProviderId(providerId);
    if (courses.isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(courses);
    }
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Long> getProviderIdByName(@PathVariable String name) {
    return providerService.getProviderIdByName(name)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Posts a new provider into the API, the provider cannot be null and responds with status
   * @param courseId The id of the course
   * @param provider The provider to be added into the database
   * @return either a bad request or created status.
   */
  @PostMapping("{courseId}")
  public ResponseEntity<CourseProvider> postProvider(@PathVariable int courseId, @RequestBody CourseProvider provider) {
    if(provider == null && userService.isAdmin()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }else {
      courseService.postProvider(courseId, provider);
      return ResponseEntity.status(HttpStatus.CREATED).body(provider);
    }
  }

  @GetMapping("/api/courses/{courseId}/providers/{providerId}/price")
  public ResponseEntity<Double> getCoursePriceByProviderIdAndCourseId(@PathVariable int courseId, @PathVariable int providerId) {
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
  public ResponseEntity<Provider> postProvider(@RequestBody Provider provider) {
    if(provider == null && userService.isAdmin()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }else {
      providerService.addProvider(provider);
      return ResponseEntity.status(HttpStatus.CREATED).body(provider);
    }
  }
}