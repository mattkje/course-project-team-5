package no.ntnu.courses.coursesapi.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Class for navigating through html files
 */
@Controller
public class SiteController {

    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    @GetMapping("/about")
    public String about() {
        return "about/index.html";
    }

    @GetMapping("/mobile")
    public String mobile() {
        return "mobile.html";
    }
}
