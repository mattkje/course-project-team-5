package no.ntnu.courses.coursesapi.api.keywords;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the CourseKeywords entity.
 */
public interface CourseKeywordsRepository extends JpaRepository<CourseKeywords, Long> {
}
