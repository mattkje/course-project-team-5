package no.ntnu.courses.coursesapi.api.course;

import no.ntnu.courses.coursesapi.api.keywords.CourseKeywords;
import no.ntnu.courses.coursesapi.api.provider.CourseProvider;

import java.util.List;


public record CourseWithProvidersAndKeywords(Course course, List<CourseProvider> providers,
                                             List<CourseKeywords> keywords) {

}
