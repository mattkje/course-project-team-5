package no.ntnu.courses.coursesapi.service;

import no.ntnu.courses.coursesapi.api.model.Course;
import no.ntnu.courses.coursesapi.api.model.CourseProviders;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
public class CourseService {

    private List<Course> courseList;

    public CourseService() throws ParseException {
        courseList = new ArrayList<>();
        List<String> keywords = List.of("test", "test");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        CourseProviders courseProviders = new CourseProviders("test provider", 1000, Currency.getInstance("NOK"));

        Course course = new Course(1, "test", "test", keywords, "Beginner",
                dateFormat.parse("2024-01-27"), 2, 10, keywords,
                courseProviders, "test description");
        //Create a read from json here!!

        courseList.add(course);
    }

    public Course getCourse(Integer id) {
        for(Course course : courseList) {
            if(id == course.getProductID()) {
                return course;
            }
        }
        return null; //Change this!
    }
}
