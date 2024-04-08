package no.ntnu.courses.coursesapi.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("desktop/index.html");
    registry.addViewController("/register").setViewName("desktop/register.html");
    registry.addViewController("/about").setViewName("desktop/about.html");
    registry.addViewController("/explore").setViewName("desktop/explore.html");
    registry.addViewController("/error").setViewName("error/error.html");
    registry.addViewController("/login").setViewName("desktop/login.html");
    registry.addViewController("/cart").setViewName("desktop/cart.html");
    registry.addViewController("/courses").setViewName("desktop/course.html");
    registry.addViewController("/mobile").setViewName("mobile/index.html");
    registry.addViewController("/m-course").setViewName("mobile/course.html");
  }
}
