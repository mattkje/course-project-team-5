package no.ntnu.api.config;


import java.util.List;

import no.ntnu.api.user.User;
import no.ntnu.api.user.UserProfileDto;
import no.ntnu.api.user.UserRepository;
import no.ntnu.api.role.Role;
import no.ntnu.api.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Provides AccessUserDetails needed for authentication
 */
@Service
public class AccessUserService implements UserDetailsService {
  @Autowired
  UserRepository userRepository;
  @Autowired
  RoleRepository roleRepository;

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
  public User getSessionUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    String username = authentication.getName();
    return userRepository.findByUsername(username).orElse(null);
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
  public String tryCreateNewUser(String username, String password, String email, String firstName, String lastName, String phoneNumber) {
    String errorMessage;
    if ("".equals(username)) {
      errorMessage = "Username cannot be empty";
    } else if (userExists(username)) {
      errorMessage = "Username already taken";
    }

    else {
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
  public void createUser(String username, String password, String email, String firstName, String lastName, String phoneNumber) {
    Role userRole = roleRepository.findByName("ROLE_USER");
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

  public boolean updateProfile(User sessionUser, UserProfileDto profileData) {
    sessionUser.setFirstName(profileData.getFirstName());
    sessionUser.setLastName(profileData.getLastName());
    sessionUser.setEmail(profileData.getEmail());
    sessionUser.setPhoneNumber(profileData.getPhoneNumber());
    userRepository.save(sessionUser);
    return true;
  }
}