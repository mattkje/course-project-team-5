package no.ntnu.api.provider;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.api.course.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a provider of a course. A provider is an entity that offers courses to users.
 * Each provider has an id and a name. A provider can offer multiple courses.
 */
@Entity
@Table(name = "providers")
public class Provider {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The provider id")
  private int providerId;

  @Column
  @Schema(description = "The provider name")
  private String name;

  @ManyToMany(mappedBy = "providers")
  @Schema(description = "The provider courses")
  private List<Course> courses = new ArrayList<>();

  /**
   * Empty constructor needed for JPA.
   */
    public Provider() {
    }

  /**
   * Get the id of the provider.
   *
   * @return The id of the provider
   */
  public int getProviderId() {
    return this.providerId;
  }

    /**
     * Get the name of the provider.
     *
     * @return The name of the provider
     */
  public String getName() {
    return this.name;
  }

  /**
   * Set the id of the provider.
   *
   * @param providerId The id of the provider
   */
  public void setProviderId(int providerId) {
    this.providerId = providerId;
  }

    /**
     * Set the name of the provider.
     *
     * @param name The name of the provider
     */
  public void setName(String name) {
    this.name = name;
  }
}