package no.ntnu.courses.coursesapi.api.provider;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseProviderRepository extends JpaRepository<CourseProvider, Long> {
}
