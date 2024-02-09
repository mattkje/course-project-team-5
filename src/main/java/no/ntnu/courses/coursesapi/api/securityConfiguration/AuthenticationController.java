package no.ntnu.courses.coursesapi.api.securityConfiguration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class AuthenticationController {

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser() {
        return null;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser() {
        return null;
    }
}