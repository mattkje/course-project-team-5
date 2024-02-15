package no.ntnu.courses.coursesapi.api.course;

import no.ntnu.courses.coursesapi.api.provider.Provider;

import java.util.List;


public record CourseProviders(Course course, List<Provider> providers) {

}
