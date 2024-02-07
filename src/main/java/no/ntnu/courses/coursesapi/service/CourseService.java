package no.ntnu.courses.coursesapi.service;

import no.ntnu.courses.coursesapi.api.model.Course;
import no.ntnu.courses.coursesapi.api.model.CourseProviders;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;

@Service
public class CourseService {

    private List<Course> courseList;

    public CourseService() throws ParseException {
        courseList = new ArrayList<>();
        //add courses to list
    }

    public Course getCourse(Integer id) {
        for(Course course : courseList) {
            if(id == course.getProductID()) {
                return course;
            }
        }
        return null; //Change this!
    }

    private void parseCourses() throws Exception {
        Object obj = new JSONParser().parse(new FileReader("courses.json"));

        JSONObject jo = (JSONObject) obj;

        String category = (String) jo.get("category");
        int productID = (int) jo.get("productID");
        String title = (String) jo.get("title");
        JSONArray keywords = (JSONArray) jo.get("keywords");
        String level = (String) jo.get("level");
        String closestCourseSession = (String) jo.get("closest-course-session");
        double ects = (double) jo.get("ects");
        int hoursPerWeek = (int) jo.get("hours-per-week");
        String relatedCertifications = (String) jo.get("related-certifications");
        JSONArray courseProviders = (JSONArray) jo.get("course-providers");
        System.out.println(courseProviders.size());
        //FIX THIS!
    }
}
