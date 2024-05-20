package no.ntnu.api.course;

import no.ntnu.api.keywords.CourseKeywords;
import no.ntnu.api.keywords.CourseKeywordsRepository;
import no.ntnu.api.provider.CourseProvider;
import no.ntnu.api.provider.CourseProviderRepository;
import no.ntnu.api.provider.Provider;
import no.ntnu.api.provider.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import java.util.*;

/**
 * Service class for the Course entity.
 */
@Service
public class CourseService {

  private final CourseRepository courseRepository;
  private final CourseProviderRepository courseProviderRepository;
  private final ProviderRepository providerRepository;
  private final CourseKeywordsRepository keywordRepository;

  /**
   * Constructor for the CourseService class.
   * @param courseRepository The repository for the Course entity.
   * @param courseProviderRepository The repository for the CourseProvider entity.
   * @param keywordRepository The repository for the CourseKeywords entity.
   */
  @Autowired
  public CourseService(CourseRepository courseRepository,
                       CourseProviderRepository courseProviderRepository,
                       CourseKeywordsRepository keywordRepository,
                       ProviderRepository providerRepository) {
    this.courseRepository = courseRepository;
    this.courseProviderRepository = courseProviderRepository;
    this.keywordRepository = keywordRepository;
    this.providerRepository = providerRepository;
  }

  /**
   * Returns all the courses in the database.
   * @return a collection of all the courses in the database.
   */
  public List<CourseWithProvidersAndKeywords> getAllCourses() {
    List<CourseWithProvidersAndKeywords> courseProviders = new ArrayList<>();
    for (int i = 0; i < courseRepository.findAll().size(); i++) {
      courseProviders.add(
          new CourseWithProvidersAndKeywords(courseRepository.findAll().get(i), getProviders(i + 1),
              getKeywords(i + 1)));
    }
    return courseProviders;
  }

  /**
   * Returns the course that has the matching id as the path variable. If the course does not exist the method will
   * return a NOT FOUND response.
   *
   * @param courseId id representing a course. All id's are different.
   * @return a Http response either containing the course with matching id or a NOT FOUND response.
   */
  public List<CourseProvider> getProviders(Integer courseId) {
    List<CourseProvider> providers = new ArrayList<>();
    for (CourseProvider provider : courseProviderRepository.findAll()) {
      if (courseId == provider.getCourseId()) {
        providers.add(provider);
      }
    }
    return providers;
  }

  /**
   * Returns the course that has the matching id as the path variable. If the course does not exist the method will.
   *
   * @param courseId id representing a course. All id's are different.
   * @return keywords with matching id or a NOT FOUND response.
   */
  public List<CourseKeywords> getKeywords(Integer courseId) {
    List<CourseKeywords> keywords = new ArrayList<>();
    for (CourseKeywords keyword : keywordRepository.findAll()) {
      if (courseId == keyword.getCourseId()) {
        keywords.add(keyword);
      }
    }
    return keywords;
  }

  /**
   * Returns the course that has the matching id as the path variable. If the course does not exist the method will.
   *
   * @param courseId id representing a course. All id's are different.
   * @return course with matching id or a NOT FOUND response.
   */
  public Course getCourseInfo(Integer courseId) {
    for (Course course : courseRepository.findAll()) {
      if (courseId == course.getCourseId()) {
        return course;
      }
    }
    return null;
  }

    /**
     * Returns the course that has the matching id as the path variable. If the course does not exist the method will.
     *
     * @param courseId id representing a course. All id's are different.
     * @return course with matching id or a NOT FOUND response.
     */
  public CourseWithProvidersAndKeywords getCourseWithProviders(Integer courseId) {
    Course course = getCourseInfo(courseId);
    List<CourseProvider> providers = getProviders(courseId);
    List<CourseKeywords> keywords = getKeywords(courseId);
    return new CourseWithProvidersAndKeywords(course, providers, keywords);
  }

  /**
   * This method should return a course with provider.
   *
   * @param providerId The provider of the course
   * @param courseId The id of the course
   * @return The course
   */
  public CourseWithProvidersAndKeywords getCourse(Integer courseId, Integer providerId,
                                                  Integer keywordId) {
    Map<Integer, CourseProvider> providerMap = new HashMap<>();
    Map<Integer, CourseKeywords> keywordMap = new HashMap<>();

    for (CourseProvider provider : courseProviderRepository.findAll()) {
      providerMap.put(provider.getCourseProviderId(), provider);
    }

    for (CourseKeywords keyword : keywordRepository.findAll()) {
      keywordMap.put(keyword.getKeywordId(), keyword);
    }

    CourseProvider provider = providerMap.get(providerId);
    if (provider == null) {
      return null;
    }

    CourseKeywords keyword = keywordMap.get(keywordId);
    if (keyword == null) {
      return null;
    }

    List<CourseProvider> providers = new ArrayList<>();
    List<CourseKeywords> keywords = new ArrayList<>();
    for (Course course : courseRepository.findAll()) {
      if (courseId.equals(provider.getCourseId()) && courseId.equals(keyword.getCourseId())) {
        providers.add(provider);
        keywords.add(keyword);
        return new CourseWithProvidersAndKeywords(course, providers, keywords);
      }
    }

    return null;
  }

  /**
   * Deletes a course from the api, the course is chosen by the given id parameter.
   *
   * @param course The course to be deleted
   */
  public void deleteCourse(Course course) {
    if (course != null) {
      courseRepository.delete(course);
    }
  }

    /**
     * Posts a new course into the API, the course cannot be null.
     *
     * @param course The course to be added into the database
     */
  public void postCourse(Course course) {
    if (course != null) {
      courseRepository.save(course);
    }
  }

  /**
   * Posts a new provider into the API, the provider cannot be null.
   *
   * @param courseId The id of the course
   * @param provider The provider to be added into the database
   */
  public void postProvider(int courseId, CourseProvider provider) {
    if (provider != null) {
      provider.setCourseId(courseId);
      courseProviderRepository.save(provider);
    }
  }

  /**
   * Deletes a provider from the api, the provider is chosen by the given id parameter.
   *
   * @param courseId The id of the course
   * @param providerId The id of the provider
   */
  public void deleteProvider(int courseId, int providerId) {
    for (CourseProvider provider : courseProviderRepository.findAll()) {
      if (provider.getCourseId() == courseId && provider.getCourseProviderId() == providerId) {
        courseProviderRepository.delete(provider);
      }
    }
  }

  public Collection<Provider> getAllProviders() {
    return providerRepository.findAll();
  }

  // Java
  public List<Course> getCoursesByProviderId(Long providerId) {
    return courseRepository.findAllByProviderId(providerId);
  }

  public Collection<Course> getCoursesByCategory(String category) {
    return courseRepository.findByCategory(category);
  }


  public Collection<Course> getCoursesByProvider(String providerName) {
    return courseRepository.findAllByProviderName(providerName);
}

  public Collection<Course> getCoursesByLevel(String level) {
    return courseRepository.findByLevel(level);
  }

  public Collection<Course> getCoursesBySize(double size) {
    return courseRepository.findBySizeGreaterThanEqual(size);
  }

  public Map<String, Double> getLowestPrice() {
    Map<String, Double> courseLowestPriceMap = new HashMap<>();
    List<Course> courses = this.fetchAllCourses();
    for (Course course : courses) {
      double lowestPrice = this.courseProviderRepository.findById(course.getId()).stream()
          .mapToDouble(CourseProvider::getPrice)
          .min()
          .orElseThrow(NoSuchElementException::new);
      courseLowestPriceMap.put(course.getName(), lowestPrice);
    }
    return courseLowestPriceMap;
  }

  public List<Course> fetchAllCourses() {
    return this.courseRepository.findAll();
  }

  public Collection<Course> getCoursesByIds(List<Integer> ids) {
    return this.courseRepository.findAllByCourseId(ids);
  }

  public Collection<Course> getCoursesWithinDateRange(String startDate, String endDate) {
    // Define the date format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Convert the startDate and endDate strings to LocalDate objects
    LocalDate start = LocalDate.parse(startDate, formatter);
    LocalDate end = LocalDate.parse(endDate, formatter);

    // Call the repository method to get the courses within the date range
    return courseRepository.findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(start, end);
  }
}
