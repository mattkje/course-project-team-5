package no.ntnu.api.course;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for the Course entity.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

  @Query("SELECT c FROM Course c JOIN c.providers p WHERE p.id = :providerId")
  List<Course> findAllByProviderId(Long providerId);

  @Query("SELECT c FROM Course c WHERE c.category = :category")
    List<Course> findByCategory(String category);

  @Query("SELECT c FROM Course c WHERE c.level = :level")
  Collection<Course> findByLevel(String level);

  @Query("SELECT c FROM Course c WHERE c.courseSize >= :size")
  List<Course> findBySizeGreaterThanEqual(double size);
}
