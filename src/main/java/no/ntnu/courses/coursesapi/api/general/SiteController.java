package no.ntnu.courses.coursesapi.api.general;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


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


}
