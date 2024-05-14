package no.ntnu.api.user;

import java.util.List;
import no.ntnu.api.course.userCourses.UserCourses;

/**
 * Data transfer object (DTO) for submitting changes to user profile data.
 *
 * @param user   The user object
 * @param courses The list of courses the user is enrolled in
 */
public record UserWithCourses(User user, List<UserCourses> courses) {
}
