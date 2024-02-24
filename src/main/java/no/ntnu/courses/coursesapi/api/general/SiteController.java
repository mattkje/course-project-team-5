package no.ntnu.courses.coursesapi.api.general;

import no.ntnu.courses.coursesapi.api.course.CourseProviders;
import no.ntnu.courses.coursesapi.api.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;


/**
 * Class for navigating through html files
 */
@Controller
public class SiteController {

    /**
     * Responds to HTTP GET requests for / which should return the home page.
     *
     * @return The Home page
     */
    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    /**
     * Responds to HTTP GET requests for /about which should return the about page.
     *
     * @return The Home page
     */
    @GetMapping("/about")
    public String about() {
        return "about.html";
    }

    /*
     * Debug methods
     */
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register.html";
    }

    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    @GetMapping("/courses/{courseId}")
    public String getCoursePage(@PathVariable int courseId) {
        logger.info("Received request for course with ID: {}", courseId);
        // Add any model attributes if needed
        return "course";
    }


}
