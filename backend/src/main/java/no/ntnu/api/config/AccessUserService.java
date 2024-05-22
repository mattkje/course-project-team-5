package no.ntnu.api.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import no.ntnu.api.course.Course;
import no.ntnu.api.course.CourseRepository;
import no.ntnu.api.course.userCourses.UserCourses;
import no.ntnu.api.course.userCourses.UserCoursesRepository;
import no.ntnu.api.role.Role;
import no.ntnu.api.role.RoleRepository;
import no.ntnu.api.user.User;
import no.ntnu.api.user.UserProfileDto;
import no.ntnu.api.user.UserRepository;
import no.ntnu.api.user.UserWithCourses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Provides AccessUserDetails needed for authentication.
 */
@Service
public class AccessUserService implements UserDetailsService {
  @Autowired
  UserRepository userRepository;
  @Autowired
  RoleRepository roleRepository;
  @Autowired
  UserCoursesRepository userCoursesRepository;
  @Autowired
  CourseRepository courseRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      return new AccessUserDetails(user.get());
    } else {
      throw new UsernameNotFoundException("User " + username + " not found");
    }
  }

  /**
   * Get the user which is authenticated for the current session.
   *
   * @return User object or null if no user has logged in
   */
  public UserWithCourses getSessionUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();

    if (authentication == null) {
      return null;
    }

    String username = authentication.getName();
    Optional<User> userOptional = userRepository.findByUsername(username);

    if (userOptional.isEmpty()) {
      return null;
    }

    User user = userOptional.get();
    List<UserCourses> courses;

    try {
      courses = getCourses(username);
    } catch (Exception e) {
      return null;
    }
    return new UserWithCourses(user, courses);
  }

  /**
   * Check if user with given username exists in the database.
   *
   * @param username Username of the user to check, case-sensitive
   * @return True if user exists, false otherwise
   */
  private boolean userExists(String username) {
    try {
      loadUserByUsername(username);
      return true;
    } catch (UsernameNotFoundException ex) {
      return false;
    }
  }

  /**
   * Try to create a new user with the given username and password.
   *
   * @param username Username of the new user
   * @param password Plaintext password of the new user
   * @return Error message if the user could not be created, null otherwise
   */
  public String tryCreateNewUser(String username, String password, String email, String firstName,
                                 String lastName, String phoneNumber) {
    String errorMessage;
    if ("".equals(username)) {
      errorMessage = "Username cannot be empty";
    } else if (userExists(username)) {
      errorMessage = "Username already taken";
    } else {
      errorMessage = checkPasswordRequirements(password);
      if (errorMessage == null) {
        createUser(username, password, email, firstName, lastName, phoneNumber);
      }
    }
    return errorMessage;
  }

  /**
   * Check if the password meets the requirements.
   *
   * @param password The password to check
   * @return Error message if the password does not meet the requirements, null otherwise
   */
  private String checkPasswordRequirements(String password) {
    String errorMessage = null;
    if (password == null || password.length() == 0) {
      errorMessage = "Password cannot be empty";
    } else if (password.length() < 8) {
      errorMessage = "Password must be at least 8 characters long";
    }
    return errorMessage;
  }

  /**
   * Create a new user with the given username and password.
   *
   * @return true if the user was created, false otherwise
   */
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Create a new user with the given username and password.
   *
   * @param username The username of the new user
   * @param password The plaintext password of the new user
   */
  public void createUser(String username, String password, String email, String firstName,
                         String lastName, String phoneNumber) {
    Role userRole = roleRepository.findOneByName("ROLE_USER");
    if (userRole != null) {
      User user = new User(username, createHash(password), email, firstName, lastName, phoneNumber);
      user.addRole(userRole);
      userRepository.save(user);
    }
  }

  /**
   * Create a hash of the given password.
   *
   * @param password The password to hash
   * @return The hashed password
   */
  private String createHash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  /**
   * Update the profile of the currently logged in user.
   *
   * @param profileData The new profile data
   * @return True if the profile was updated, false otherwise
   */
  public boolean updateProfile(UserWithCourses sessionUser, UserProfileDto profileData) {
    sessionUser.user().setFirstName(profileData.getFirstName());
    sessionUser.user().setLastName(profileData.getLastName());
    sessionUser.user().setEmail(profileData.getEmail());
    sessionUser.user().setPhoneNumber(profileData.getPhoneNumber());
    userRepository.save(sessionUser.user());
    return true;
  }

  /**
   * Change the password of the currently logged in user.
   *
   * @param oldPassword The old password
   * @param newPassword The new password
   */
  public void changePassword(UserWithCourses sessionUser, String oldPassword, String newPassword) {
    String currentPasswordHash = sessionUser.user().getPassword();

    if (BCrypt.checkpw(oldPassword, currentPasswordHash)) {
      if (newPassword.length() < 8) {
        throw new IllegalArgumentException("Password must be at least 8 characters long");
      }
      sessionUser.user().setPassword(createHash(newPassword));
      userRepository.save(sessionUser.user());
    } else {
      throw new IllegalArgumentException("Old password is incorrect");
    }
  }

  /**
   * Get the courses of the currently logged in user.
   *
   * @return List of courses
   */
  public List<UserCourses> getCourses(String username) {
    List<UserCourses> courses = new ArrayList<>();
    for (UserCourses userCourse : userCoursesRepository.findAll()) {
      if (userCourse.getUserId() == userRepository.findByUsername(username).get().getUserId()) {
        courses.add(userCourse);
      }
    }
    return courses;
  }

  /**
   * Delete the currently logged in user.
   */
  public void deleteUser(String username) {
    for (User user : userRepository.findAll()) {
      if (user.getUsername().equals(username)) {
        userRepository.delete(user);
      }
    }
  }

  /**
   * Check if the currently logged in user is an admin.
   *
   * @return True if the user is an admin, false otherwise
   */
  public boolean isAdmin() {
    UserWithCourses sessionUser = getSessionUser();
    if (sessionUser != null) {
      for (Role role : sessionUser.user().getRoles()) {
        if (role.getName().equals("ROLE_ADMIN")) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Add a role to the user with the given username.
   *
   * @param username The username of the user
   * @param role     The role to add
   */
  public void addRole(String username, String role) {
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isPresent()) {
      User user = userRepository.findByUsername(username).get();
      user.addRole(roleRepository.findOneByName(role));
      userRepository.save(user);
    }
  }

  /**
   * Delete a role from the user with the given username.
   *
   * @param username The username of the user
   * @param role     The role to delete
   */
  public void deleteRole(String username, String role) {
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isPresent()) {
      User user = userRepository.findByUsername(username).get();
      user.getRoles().remove(roleRepository.findOneByName(role));
      userRepository.save(user);
    }
  }

  /**
   * Purchase a pro subscription for the currently logged in user.
   *
   * @param subscriptionType The type of subscription to purchase
   */
  public void purchasePro(User user, String subscriptionType) {
    LocalDate today = LocalDate.now();
    LocalDate endDate;

    if (subscriptionType.equals("1-month")) {
      endDate = today.plusMonths(1);
    } else if (subscriptionType.equals("3-months")) {
      endDate = today.plusMonths(3);
    } else if (subscriptionType.equals("12-months")) {
      endDate = today.plusMonths(12);
    } else {
      throw new IllegalArgumentException("Invalid subscription type");
    }

    user.addRole(roleRepository.findOneByName("ROLE_PRO"));
    user.setSubscriptionExpire(endDate);
    userRepository.save(user);
  }

  /**
   * Get all users with a pro subscription that has expired.
   *
   * @param now The current date
   * @return List of users with expired pro subscriptions
   */
  public List<User> findBySubscriptionEndDateBefore(LocalDate now) {
    List<User> users = new ArrayList<>();
    for (User user : userRepository.findAll()) {
      if (user.getSubscriptionExpire().isBefore(now)) {
        users.add(user);
      }
    }
    return users;
  }

  /**
   * Remove the expiration date from the currently logged in user.
   */
  public void removeExpiration(User user) {
    user.setSubscriptionExpire(null);
    userRepository.save(user);
  }

  /**
   * Delete the pro role from the currently logged in user.
   */
  public void deleteProRole(String username) {
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isPresent()) {
      User user = userRepository.findByUsername(username).get();
      user.getRoles().remove(roleRepository.findOneByName("ROLE_PRO"));
      userRepository.save(user);
    }
  }

  /**
   * Check if the currently logged in user is a pro user.
   *
   * @return True if the user is a pro user, false otherwise
   */
  public boolean isPro() {
    UserWithCourses sessionUser = getSessionUser();
    if (sessionUser != null) {
      for (Role role : sessionUser.user().getRoles()) {
        if (role.getName().equals("ROLE_PRO")) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Checks if the role is user.
   *
   * @return True if the user is a user, false otherwise
   */
  public boolean isUser() {
    UserWithCourses sessionUser = getSessionUser();
    if (sessionUser != null) {
      for (Role role : sessionUser.user().getRoles()) {
        if (role.getName().equals("ROLE_USER")) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get the user with their courses.
   *
   * @param username The username of the user
   * @return The user with the given username
   */
  public UserWithCourses getUser(String username) {
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isPresent()) {
      User user = userRepository.findByUsername(username).get();
      List<UserCourses> courses = getCourses(username);
      return new UserWithCourses(user, courses);
    }
    return null;
  }

  /**
   * Unsubscribe the currently logged in user from the pro subscription.
   */
  public void unsubscribe(User user) {
    deleteProRole(user.getUsername());
  }

  /**
   * Add a course to the currently logged in user.
   *
   * @param courseId The ID of the course to add
   */
  public void addUserCourse(User user, int courseId) {
    for (Course course : courseRepository.findAll()) {
      if (course.getCourseId() == courseId) {
        UserCourses userCourse = new UserCourses();
        userCourse.setUserId(user.getUserId());
        userCourse.setCourse(course);
        userCoursesRepository.save(userCourse);
      }
    }
  }

  /**
   * Adds a profile image to the user.
   *
   * @param image The image to add
   */
  public void addImageToUser(String image) {
    if (getSessionUser() != null && image != null) {
      image = image.replace("\"", "");
      getSessionUser().user().setImage(image);
      userRepository.save(getSessionUser().user());
    }
  }

  public String getImage(String username) {
    return getUser(username).user().getImage();
  }
}