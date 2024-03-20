package no.ntnu.courses.coursesapi.api.general;

import no.ntnu.courses.coursesapi.api.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SiteController {

    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    @GetMapping("/")
    public String home() {
        return "desktop/index.html";
    }

    @GetMapping("/about")
    public String about() {
        return "desktop/about.html";
    }

    @GetMapping("/explore")
    public String explore() {
        return "desktop/explore.html";
    }

    @GetMapping("/error")
    public String error() {
        return "error/error.html";
    }

    @GetMapping("/login")
    public String login() {
        return "desktop/login.html";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "desktop/register.html";
    }

    @GetMapping("/courses")
    public String getProductPage() {
        return "desktop/course.html";
    }

    @GetMapping("/mobile")
    public String mobile() {
        return "mobile/index.html";
    }

    @GetMapping("/m-courses")
    public String getMobileProductPage() {
        return "mobile/course.html";
    }
}
