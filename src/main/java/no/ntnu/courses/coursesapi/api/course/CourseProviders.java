package no.ntnu.courses.coursesapi.api.course;

import no.ntnu.courses.coursesapi.api.provider.Provider;



public class CourseProviders {
    private final Course course;
    private final Provider provider;

    public CourseProviders(Course course, Provider provider) {
        this.course = course;
        this.provider = provider;
    }

    public Course getCourse() {
        return course;
    }

    public Provider getProvider() {
        return provider;
    }

}
