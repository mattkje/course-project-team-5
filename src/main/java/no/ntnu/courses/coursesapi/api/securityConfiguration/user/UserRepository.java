package no.ntnu.courses.coursesapi.api.securityConfiguration.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}