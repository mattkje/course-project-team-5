package no.ntnu.courses.coursesapi.api.provider;

import jakarta.persistence.*;

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