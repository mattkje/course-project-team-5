package no.ntnu.api.provider;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for the CourseProvider entity.
 */
public interface CourseProviderRepository extends JpaRepository<CourseProvider, Long> {
  @Query("SELECT cp FROM CourseProvider cp WHERE cp.courseId = :courseId AND cp.courseProviderId = :providerId")
  CourseProvider findByCourseIdAndProviderId(@Param("courseId") int courseId, @Param("providerId") int providerId);
}
