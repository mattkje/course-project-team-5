package no.ntnu.courses.coursesapi.api.course;

import no.ntnu.courses.coursesapi.api.course.CourseRepository;
import no.ntnu.courses.coursesapi.api.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Integer id) {
        for(Course course : courseRepository.findAll()) {
            if(id == course.getProductID()) {
                return course;
            }
        }
        return null;
    }

}
