package no.ntnu.courses.coursesapi.api.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the User entity.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}