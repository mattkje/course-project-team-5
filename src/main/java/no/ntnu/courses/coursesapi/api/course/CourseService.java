package no.ntnu.courses.coursesapi.api.course;

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

    public List<CourseProviders> getAllCourses() {
        List<CourseProviders> courseProviders = new ArrayList<>();
        for (int i = 0; i < courseRepository.findAll().size(); i++) {
            courseProviders.add(new CourseProviders(courseRepository.findAll().get(i), getProviders(i+1)));
        }
        return courseProviders;
    }


    public List<Provider> getProviders(Integer courseId) {
        List<Provider> providers = new ArrayList<>();
        for(Provider provider : providerRepository.findAll()) {
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

    public CourseProviders getCourseWithProviders(Integer courseId) {
        Course course = getCourseInfo(courseId);
        List<Provider> providers = getProviders(courseId);
        return new CourseProviders(course, providers);
    }

    /**
     * This method should return a course with provider.
     *
     * @param providerId The provider of the course
     * @param courseId The id of the course
     * @return The course
     */
    public CourseProviders getCourse(Integer courseId, Integer providerId) {
        Map<Integer, Provider> providerMap = new HashMap<>();
        for (Provider provider : providerRepository.findAll()) {
            providerMap.put(provider.getProviderId(), provider);
        }

        Provider provider = providerMap.get(providerId);
        if (provider == null) {
            return null;
        }

        List<Provider> providers = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            if (courseId.equals(provider.getCourseId())) {
                providers.add(provider);
                return new CourseProviders(course, providers);
            }
        }

        return null;
    }
}
