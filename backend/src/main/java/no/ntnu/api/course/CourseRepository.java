package no.ntnu.api.course;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the Course entity.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

}
