package no.ntnu.courses.coursesapi.api.general;

import no.ntnu.courses.coursesapi.api.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register.html";
    }

}
