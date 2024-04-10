package no.ntnu.courses.coursesapi.api.provider;

import jakarta.persistence.*;
import no.ntnu.courses.coursesapi.api.course.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a provider of a course.
 */
@Entity
@Table(name = "providers")
public class Provider {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int providerId;

  @Column
  private String name;

  @ManyToMany(mappedBy = "providers")
  private List<Course> courses = new ArrayList<>();

    public Provider() { // This constructor is empty due to the use of the @Entity annotation

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