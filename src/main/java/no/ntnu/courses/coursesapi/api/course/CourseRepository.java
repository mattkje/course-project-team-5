package no.ntnu.courses.coursesapi.api.course;

import no.ntnu.courses.coursesapi.api.provider.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT u FROM Course u WHERE u.courseId = ?1")
    public List<Provider> findAllProvidersByCourseId(int courseId);
}
