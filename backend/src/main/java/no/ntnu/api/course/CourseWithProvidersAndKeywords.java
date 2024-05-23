package no.ntnu.api.course;

import java.util.List;
import no.ntnu.api.keywords.CourseKeywords;
import no.ntnu.api.provider.CourseProvider;


/**
 * Represents a course with its providers and keywords.
 *
 * @param course A course
 * @param providers List of providers
 * @param keywords List of keywords
 */
public record CourseWithProvidersAndKeywords(Course course, List<CourseProvider> providers,
                                             List<CourseKeywords> keywords) {

}
