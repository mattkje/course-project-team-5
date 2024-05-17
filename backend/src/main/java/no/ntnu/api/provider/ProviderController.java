package no.ntnu.api.provider;

import no.ntnu.api.course.Course;
import no.ntnu.api.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

  private final ProviderService providerService;
  private final CourseService courseService;

  @Autowired
  public ProviderController(ProviderService providerService, CourseService courseService) {
    this.providerService = providerService;
    this.courseService = courseService;
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
}