package no.ntnu.api.course.community;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the Course entity.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

}