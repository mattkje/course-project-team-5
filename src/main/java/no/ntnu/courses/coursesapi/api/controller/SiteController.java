package no.ntnu.courses.coursesapi.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
        return "about/index.html";
    }

    /*
     * Debug methods
     */
    @GetMapping("/mobile")
    public String mobile() {
        return "mobile.html";
    }
}
