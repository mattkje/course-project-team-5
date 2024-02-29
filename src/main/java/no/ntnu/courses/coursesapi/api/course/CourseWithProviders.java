package no.ntnu.courses.coursesapi.api.course;

import no.ntnu.courses.coursesapi.api.provider.CourseProvider;

import java.util.List;


public record CourseWithProviders(Course course, List<CourseProvider> providers) {

}
