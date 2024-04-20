package no.ntnu.api.user;

import java.util.List;
import no.ntnu.api.course.userCourses.UserCourses;

public record UserWithCourses(User user, List<UserCourses> courses) {
}
