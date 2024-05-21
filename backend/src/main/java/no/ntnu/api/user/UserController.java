package no.ntnu.api.user;

import no.ntnu.api.config.*;
import no.ntnu.api.course.userCourses.UserCourses;
import no.ntnu.api.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for handling user requests
 */
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccessUserService userService;


    /**
     * Get all users.
     *
     * @return A list of all users
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        if(userService.isAdmin()) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only accessible to authorized users");
        }
    }

    /**
     * Register a new user.
     *
     * @param request The registration request
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        String errorMessage = userService.tryCreateNewUser(request.getUsername(), request.getPassword(),
                request.getEmail(), request.getFirstName(), request.getLastName(), request.getPhoneNumber());
        ResponseEntity<String> response;
        if (errorMessage == null) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Authenticate a user.
     *
     * @param authenticationRequest The authentication request
     * @return The authentication response
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userService.loadUserByUsername(
            authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    /**
     * Get the profile of a user.
     *
     * @param username The username of the user
     * @return The profile of the user
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> getProfile(@PathVariable String username) {
        if(userService.getSessionUser() != null) {
            if(userService.isAdmin() || userService.getSessionUser().user().getUsername().equals(username)){
                if(userService.getUser(username) == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }
                return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(username));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only accessible to authorized users");
            }
        }
        return null;
    }

    /**
     * Update the profile of a user.
     *
     * @param username The username of the user
     * @param profileData The new profile data
     * @return The response
     * @throws InterruptedException
     */
    @PutMapping("/{username}")
    public ResponseEntity<String> updateProfile(@PathVariable String username, @RequestBody UserProfileDto profileData)
            throws InterruptedException {
        UserWithCourses sessionUser = userService.getSessionUser();
        ResponseEntity<String> response;
        if (sessionUser != null && sessionUser.user().getUsername().equals(username)) {
            if (profileData != null) {
                if(userService.updateProfile(sessionUser, profileData)) {
                    Thread.sleep(2000); // Simulate long operation
                    response = new ResponseEntity<>("", HttpStatus.OK);
                } else {
                    response = new ResponseEntity<>("Could not update Profile data", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                response = new ResponseEntity<>("Profile data not supplied", HttpStatus.BAD_REQUEST);
            }
        } else if (sessionUser == null) {
            response = new ResponseEntity<>("Profile data accessible only to authenticated users", HttpStatus.UNAUTHORIZED);
        } else {
            response = new ResponseEntity<>("Profile data for other users not accessible!", HttpStatus.FORBIDDEN);
        }
        return response;
    }

    /**
     * Change the password of a user.
     *
     * @param username The username of the user
     * @param details The new password
     * @return The response
     */
    @PutMapping("/{username}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable String username, @RequestBody ChangePasswordRequest details) {
        if(details.getNewPassword().length() < 8) {
            return new ResponseEntity<>("Password must be at least 8 characters long", HttpStatus.BAD_REQUEST);
        }
        UserWithCourses sessionUser = userService.getSessionUser();
        if (sessionUser != null && sessionUser.user().getUsername().equals(username)) {
            if(details.getNewPassword() != null) {
                userService.changePassword(sessionUser, details.getOldPassword(), details.getNewPassword());
                return new ResponseEntity<>("", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Password not supplied", HttpStatus.BAD_REQUEST);
            }
        } else if (sessionUser == null){
            return new ResponseEntity<>("Profile data accessible only to authenticated users", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Profile data for other users not accessible!", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{username}/change-image")
    public ResponseEntity<?> changeProfilePicture(@PathVariable String username, @RequestBody String image) {
        UserWithCourses sessionUser = userService.getSessionUser();
        if (sessionUser != null && sessionUser.user().getUsername().equals(username)) {
            userService.addImageToUser(image);
            return new ResponseEntity<>("", HttpStatus.OK);
        } else if (sessionUser == null){
            return new ResponseEntity<>("Profile data accessible only to authenticated users", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Profile data for other users not accessible!", HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Delete a user.
     *
     * @param username The username of the user
     * @return The response
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        for(User user : userService.getAllUsers()) {
            if(user.getUsername().equals(username)) {
                UserWithCourses sessionUser = userService.getSessionUser();
                if(sessionUser != null && sessionUser.user().getUsername().equals(username) || userService.isAdmin()) {
                    userService.deleteUser(username);
                    return ResponseEntity.status(HttpStatus.OK).body("User successfully deleted");
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only accessible to authorized users");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    /**
     * Add a role to a user.
     *
     * @param username The username of the user
     * @param role The role to add
     * @return The response
     */
    @PutMapping("/{username}/add-role")
    public ResponseEntity<?> addRole(@PathVariable String username, @RequestBody String role) {
        if(userService.isAdmin()) {
            userService.addRole(username, role);
            return ResponseEntity.status(HttpStatus.OK).body("Role successfully added");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only accessible to authorized users");
        }
    }

    /**
     * Delete a role from a user.
     *
     * @param username The username of the user
     * @param role The role to delete
     * @return The response
     */
    @PutMapping("/{username}/delete-role")
    public ResponseEntity<?> deleteRole(@PathVariable String username, @RequestBody String role) {
        if(userService.isAdmin()) {
            userService.deleteRole(username, role);
            return ResponseEntity.status(HttpStatus.OK).body("Role successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only accessible to authorized users");
        }
    }

    /**
     * Refresh a jwt token.
     * @param tokenRefreshRequest The refresh token request
     * @return The response
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        try {
            String refreshToken = tokenRefreshRequest.getRefreshToken();
            UserDetails userDetails = userService.loadUserByUsername(jwtUtil.extractUsername(refreshToken));
            if (jwtUtil.validateToken(refreshToken, userDetails)) {
                String newJwtToken = jwtUtil.refreshToken(refreshToken, userDetails);
                return ResponseEntity.ok(new AuthenticationResponse(newJwtToken));
            } else {
                return ResponseEntity.badRequest().body("Invalid refresh token");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Allow user to buy a pro subscription.
     *
     * @param subscriptionType The type of subscription
     * @return The response
     */
    @PutMapping("/purchase-pro/{subscriptionType}")
    public ResponseEntity<?> purchasePro(@PathVariable String subscriptionType) {
        UserWithCourses sessionUser = userService.getSessionUser();
        if (sessionUser != null) {
            if (userService.isPro()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user already has the Pro role");
            }
            userService.purchasePro(sessionUser.user(), subscriptionType);
            return ResponseEntity.status(HttpStatus.OK).body("Pro role successfully added");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Profile data accessible only to authenticated users");
        }
    }

    @PutMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe() {
        UserWithCourses sessionUser = userService.getSessionUser();
        if (sessionUser != null) {
            if (!userService.isPro()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user does not have the Pro role");
            }
            userService.unsubscribe(sessionUser.user());
            return ResponseEntity.status(HttpStatus.OK).body("Pro role successfully removed");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Profile data accessible only to authenticated users");
        }
    }

    /**
     * Check for expired subscriptions and remove pro role if expired.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkExpiredSubscriptions() {
        LocalDate now = LocalDate.now();
        List<User> usersWithExpiredSubscriptions = userService.findBySubscriptionEndDateBefore(now);
        for (User user : usersWithExpiredSubscriptions) {
            userService.deleteProRole(user.getUsername());
            userService.removeExpiration(user);
        }
    }

    @PostMapping("/add-course/{courseId}")
    public ResponseEntity<?> addCourse(@PathVariable int courseId) {
        UserWithCourses sessionUser = userService.getSessionUser();
        if (sessionUser != null) {
            userService.addUserCourse(sessionUser.user(), courseId);
            return ResponseEntity.status(HttpStatus.OK).body("Course successfully added");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Profile data accessible only to authenticated users");
    }
}
