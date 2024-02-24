package no.ntnu.courses.coursesapi.api.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }




    @PostMapping("/register")
    public User register(@RequestBody RegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.USER);
        user.setCreatedAt();
        user.setUpdatedAt();
        user.setEnabled(true);

        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> payload) {
        String username = (String) payload.get("username");
        String password = (String) payload.get("password");


        User user = userService.findByUsername(username);


        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Invalid username or password."), HttpStatus.UNAUTHORIZED);
        } else {

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
