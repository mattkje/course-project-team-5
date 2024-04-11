package no.ntnu.courses.coursesapi.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for the MVC.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Value("${frontend.access.url}")
  private String frontendUrl;

  /**
   * This method allows frontend to access the backend.
   *
   * @param registry The registry to add the mapping to.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(frontendUrl)
        .allowedMethods("*")
        .allowedHeaders("*");
  }
}
