package no.ntnu.courses.coursesapi.api.course;

import no.ntnu.courses.coursesapi.api.course.CourseRepository;
import no.ntnu.courses.coursesapi.api.course.Course;
import no.ntnu.courses.coursesapi.api.provider.Provider;
import no.ntnu.courses.coursesapi.api.provider.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ProviderRepository providerRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, ProviderRepository providerRepository) {
        this.courseRepository = courseRepository;
        this.providerRepository = providerRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseInfo(Integer courseId) {
        for(Course course : courseRepository.findAll()) {
            if(courseId == course.getProductID()) {
                return course;
            }
        }
        return null;
    }

    /**
     * This method should return a course with provider.
     *
     * @param providerId The provider of the course
     * @param courseId The id of the course
     * @return The course
     */
    public CourseProviders getCourse(Integer courseId, Integer providerId) {
        for(Provider provider : providerRepository.findAll()) {
            if(providerId == provider.getProviderId()) {
                for(Course course : courseRepository.findAll()) {
                    if(courseId == provider.getCourseId()) {
                        return new CourseProviders(course, provider);
                    }
                }

            }
        }
        return null;
    }

}
