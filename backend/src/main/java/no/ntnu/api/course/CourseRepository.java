package no.ntnu.api.course;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for the Course entity.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

  @Query("SELECT c FROM Course c JOIN c.providers p WHERE p.providerId = :providerId")
  List<Course> findAllByProviderId(Long providerId);

  @Query("SELECT c FROM Course c WHERE c.category = :category")
    List<Course> findByCategory(String category);

  @Query("SELECT c FROM Course c WHERE c.level = :level")
  Collection<Course> findByLevel(String level);

  @Query("SELECT c FROM Course c WHERE c.courseSize >= :size")
  List<Course> findBySizeGreaterThanEqual(double size);

  @Query("SELECT c FROM Course c WHERE c.id IN :ids")
  Collection<Course> findAllByCourseId(@Param("ids") List<Integer> ids);

  @Query("SELECT c FROM Course c JOIN c.providers p WHERE p.name = :providerName")
Collection<Course> findAllByProviderName(@Param("providerName") String providerName);

  @Query("SELECT c FROM Course c WHERE c.start_date >= :start AND c.end_date <= :end")
  Collection<Course> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(
      @Param("start") LocalDate start, @Param("end") LocalDate end);
}
