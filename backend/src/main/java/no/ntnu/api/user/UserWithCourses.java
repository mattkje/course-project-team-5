package no.ntnu.api.user;

import java.util.List;

public record UserWithCourses(User user, List<UserCourses> courses) {
}
