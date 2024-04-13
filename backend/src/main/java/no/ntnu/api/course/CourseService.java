package no.ntnu.api.course;

import no.ntnu.api.keywords.CourseKeywords;
import no.ntnu.api.keywords.CourseKeywordsRepository;
import no.ntnu.api.provider.CourseProvider;
import no.ntnu.api.provider.CourseProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for the Course entity.
 */
@Service
public class CourseService {

  private final CourseRepository courseRepository;
  private final CourseProviderRepository providerRepository;
  private final CourseKeywordsRepository keywordRepository;

  /**
   * Constructor for the CourseService class.
   * @param courseRepository The repository for the Course entity.
   * @param providerRepository The repository for the CourseProvider entity.
   * @param keywordRepository The repository for the CourseKeywords entity.
   */
  @Autowired
  public CourseService(CourseRepository courseRepository,
                       CourseProviderRepository providerRepository,
                       CourseKeywordsRepository keywordRepository) {
    this.courseRepository = courseRepository;
    this.providerRepository = providerRepository;
    this.keywordRepository = keywordRepository;
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
    for (CourseProvider provider : providerRepository.findAll()) {
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

    for (CourseProvider provider : providerRepository.findAll()) {
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
      providerRepository.save(provider);
    }
  }

  /**
   * Deletes a provider from the api, the provider is chosen by the given id parameter.
   *
   * @param courseId The id of the course
   * @param providerId The id of the provider
   */
  public void deleteProvider(int courseId, int providerId) {
    for (CourseProvider provider : providerRepository.findAll()) {
      if (provider.getCourseId() == courseId && provider.getCourseProviderId() == providerId) {
        providerRepository.delete(provider);
      }
    }
  }
}