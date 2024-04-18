package no.ntnu.api.course.community;

import java.util.Collection;
import no.ntnu.api.course.Course;
import no.ntnu.api.course.CourseWithProvidersAndKeywords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the Course entity.
 */
@Service
public class CommunityCourseService {

  private final CommunityCourseRepository courseRepository;

  /**
   * Constructor for the CourseService class.
   * @param courseRepository The repository for the Course entity.
   */
  @Autowired
  public CommunityCourseService(CommunityCourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }


  /**
   * Returns the course that has the matching id as the path variable. If the course does not exist the method will.
   *
   * @param courseId id representing a course. All id's are different.
   * @return course with matching id or a NOT FOUND response.
   */
  public CommunityCourse getCourseInfo(Integer courseId) {
    for (CommunityCourse course : courseRepository.findAll()) {
      if (courseId == course.getCourseId()) {
        return course;
      }
    }
    return null;
  }

    /**
     * Posts a new course into the API, the course cannot be null.
     *
     * @param post The course to be added into the database
     */
  public void postCourse(CommunityCourse post) {
    if (post != null) {
      courseRepository.save(post);
    }
  }

  public Collection<CommunityCourse> getAllCourses() {
    return courseRepository.findAll();
  }

  public CommunityCourse getCourse(int id) {
    return courseRepository.findById((long) id).orElse(null);
  }

  /**
   * Updates an existing course in the database.
   * @param id The ID of the course to update.
   * @param updatedCourse The updated course object.
   * @return true if the update was successful, false otherwise.
   */
  public boolean updateCourse(int id, CommunityCourse updatedCourse) {
    CommunityCourse existingCourse = courseRepository.findById((long) id).orElse(null);
    if (existingCourse != null) {

      existingCourse.setTitle(updatedCourse.getTitle());
      existingCourse.setCategory(updatedCourse.getCategory());
      existingCourse.setDescription(updatedCourse.getDescription());
      existingCourse.setContent(updatedCourse.getContent());
      existingCourse.setImage(updatedCourse.getImage());

      courseRepository.save(existingCourse);
      return true;
    } else {
      return false; // Course with given id not found
    }
  }


}
