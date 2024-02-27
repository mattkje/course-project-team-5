package no.ntnu.courses.coursesapi.api.provider;


import no.ntnu.courses.coursesapi.api.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
