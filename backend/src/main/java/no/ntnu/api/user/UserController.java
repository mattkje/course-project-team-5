package no.ntnu.api.user;

import no.ntnu.api.config.AccessUserService;
import no.ntnu.api.config.AuthenticationRequest;
import no.ntnu.api.config.AuthenticationResponse;
import no.ntnu.api.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public List<User> getAllUsers() {
        return userService.getAllUsers();
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
     * @throws InterruptedException
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> getProfile(@PathVariable String username) throws InterruptedException {
        User sessionUser = userService.getSessionUser();
        if (sessionUser != null && sessionUser.getUsername().equals(username)) {
            UserProfileDto profile = new UserProfileDto(sessionUser.getUsername(), sessionUser.getFirstName(),
                    sessionUser.getLastName(), sessionUser.getEmail(), sessionUser.getPhoneNumber());
            Thread.sleep(2000); // Simulate sleep
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else if (sessionUser == null) {
            return new ResponseEntity<>("Profile data accessible only to authenticated users", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("Profile data for other users not accessible!", HttpStatus.FORBIDDEN);
        }
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
        User sessionUser = userService.getSessionUser();
        ResponseEntity<String> response;
        if (sessionUser != null && sessionUser.getUsername().equals(username)) {
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

}