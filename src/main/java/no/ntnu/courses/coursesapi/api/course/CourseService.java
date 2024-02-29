package no.ntnu.courses.coursesapi.api.course;

import no.ntnu.courses.coursesapi.api.provider.CourseProvider;
import no.ntnu.courses.coursesapi.api.provider.CourseProviderRepository;
import no.ntnu.courses.coursesapi.api.provider.Provider;
import no.ntnu.courses.coursesapi.api.provider.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseProviderRepository providerRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseProviderRepository providerRepository) {
        this.courseRepository = courseRepository;
        this.providerRepository = providerRepository;
    }

    public List<CourseWithProviders> getAllCourses() {
        List<CourseWithProviders> courseProviders = new ArrayList<>();
        for (int i = 0; i < courseRepository.findAll().size(); i++) {
            courseProviders.add(new CourseWithProviders(courseRepository.findAll().get(i), getProviders(i+1)));
        }
        return courseProviders;
    }


    public List<CourseProvider> getProviders(Integer courseId) {
        List<CourseProvider> providers = new ArrayList<>();
        for(CourseProvider provider : providerRepository.findAll()) {
            if (courseId == provider.getCourseId()) {
                providers.add(provider);
            }
        }
        return providers;
    }

    public Course getCourseInfo(Integer courseId) {
        for(Course course : courseRepository.findAll()) {
            if(courseId == course.getCourseId()) {
                return course;
            }
        }
        return null;
    }

    public CourseWithProviders getCourseWithProviders(Integer courseId) {
        Course course = getCourseInfo(courseId);
        List<CourseProvider> providers = getProviders(courseId);
        return new CourseWithProviders(course, providers);
    }

    /**
     * This method should return a course with provider.
     *
     * @param providerId The provider of the course
     * @param courseId The id of the course
     * @return The course
     */
    public CourseWithProviders getCourse(Integer courseId, Integer providerId) {
        Map<Integer, CourseProvider> providerMap = new HashMap<>();
        for (CourseProvider provider : providerRepository.findAll()) {
            providerMap.put(provider.getCourseProviderId(), provider);
        }

        CourseProvider provider = providerMap.get(providerId);
        if (provider == null) {
            return null;
        }

        List<CourseProvider> providers = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            if (courseId.equals(provider.getCourseId())) {
                providers.add(provider);
                return new CourseWithProviders(course, providers);
            }
        }

        return null;
    }
    
    public void deleteCourse(Course course) {
        if(course != null) {
            courseRepository.delete(course);
        }
    }

    public void postCourse(Course course) {
        if(course != null) {
            courseRepository.save(course);
        }
    }


}
