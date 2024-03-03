package no.ntnu.courses.coursesapi.api.course;

import no.ntnu.courses.coursesapi.api.keywords.CourseKeywords;
import no.ntnu.courses.coursesapi.api.keywords.CourseKeywordsRepository;
import no.ntnu.courses.coursesapi.api.keywords.Keywords;
import no.ntnu.courses.coursesapi.api.provider.CourseProvider;
import no.ntnu.courses.coursesapi.api.provider.CourseProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseProviderRepository providerRepository;
    private final CourseKeywordsRepository keywordRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseProviderRepository providerRepository, CourseKeywordsRepository keywordRepository) {
        this.courseRepository = courseRepository;
        this.providerRepository = providerRepository;
        this.keywordRepository = keywordRepository;
    }

    public List<CourseWithProvidersAndKeywords> getAllCourses() {
        List<CourseWithProvidersAndKeywords> courseProviders = new ArrayList<>();
        for (int i = 0; i < courseRepository.findAll().size(); i++) {
            courseProviders.add(new CourseWithProvidersAndKeywords(courseRepository.findAll().get(i), getProviders(i+1), getKeywords(i+1)));
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

    public List<CourseKeywords> getKeywords(Integer courseId) {
        List<CourseKeywords> keywords = new ArrayList<>();
        for(CourseKeywords keyword : keywordRepository.findAll()) {
            if (courseId == keyword.getCourseId()) {
                keywords.add(keyword);
            }
        }
        return keywords;
    }

    public Course getCourseInfo(Integer courseId) {
        for(Course course : courseRepository.findAll()) {
            if(courseId == course.getCourseId()) {
                return course;
            }
        }
        return null;
    }

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
    public CourseWithProvidersAndKeywords getCourse(Integer courseId, Integer providerId, Integer keywordId) {
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


    public void postProvider(int courseId, CourseProvider provider) {
        if(provider != null) {
            provider.setCourseId(courseId);
            providerRepository.save(provider);
        }
    }

    public void deleteProvider(int courseId, int providerId) {
        for (CourseProvider provider : providerRepository.findAll()) {
            if (provider.getCourseId() == courseId && provider.getCourseProviderId() == providerId) {
                providerRepository.delete(provider);
            }
        }
    }
}
