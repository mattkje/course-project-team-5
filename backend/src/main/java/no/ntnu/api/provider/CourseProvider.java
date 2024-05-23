package no.ntnu.api.provider;


import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * The CourseProvider class is a JPA entity that represents
 * the course_providers table in the database.
 * It is used to store information about the providers of a course,
 * including the price, currency and location.
 */
@Entity
@Table(name = "course_providers")
public class CourseProvider {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_provider_id")
  private int courseProviderId;
  @Column(name = "course_id")
  private int courseId;

  @ManyToOne
  @JoinColumn(name = "provider_id")
  private Provider provider;

  @Column
  private float price;
  @Column
  private String currency;

  @Column(precision = 9, scale = 6)
  private BigDecimal latitude;

  @Column(precision = 9, scale = 6)
  private BigDecimal longitude;

  /**
   * Get the id of the course provider.
   *
   * @return The id of the course provider
   */
  public int getCourseProviderId() {
    return this.courseProviderId;
  }

  /**
   * Get the id of the course.
   *
   * @return The id of the course
   */
  public int getCourseId() {
    return this.courseId;
  }

  /**
   * Get the id of the provider.
   *
   * @return The id of the provider
   */
  public float getPrice() {
    return this.price;
  }

  /**
   * Get the currency of the price.
   *
   * @return The currency of the price
   */
  public String getCurrency() {
    return this.currency;
  }

  /**
   * Set the id of the course provider.
   *
   * @param courseProviderId The id of the course provider
   */
  public void setCourseProviderId(int courseProviderId) {
    this.courseProviderId = courseProviderId;
  }

  /**
   * Set the price of the course.
   *
   * @param price The price of the course
   */
  public void setPrice(int price) {
    this.price = price;
  }

  /**
   * Set the currency of the price.
   *
   * @param currency The currency of the price
   */
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  /**
   * Get the provider of the course.
   *
   * @return The provider of the course
   */
  public Provider getProvider() {
    return provider;
  }

  /**
   * Set the provider of the course.
   *
   * @param provider The provider of the course
   */
  public void setProvider(Provider provider) {
    this.provider = provider;
  }

  /**
   * Get the name of the provider.
   *
   * @return The name of the provider
   */
  public String getName() {
    return provider.getName();
  }

  /**
   * Set the name of the provider.
   *
   * @param providerName The name of the provider
   */
  public void setName(String providerName) {
    this.provider.setName(providerName);
  }

  /**
   * Set the id of the course.
   *
   * @param courseId The id of the course
   */
  public void setCourseId(int courseId) {
    this.courseId = courseId;
  }

  /**
   * Get the latitude of the provider location.
   *
   * @return The latitude of the provider location
   */
  public BigDecimal getLatitude() {
    return latitude;
  }

  /**
   * Set the latitude of the provider location.
   *
   * @param latitude The latitude of the provider location
   */
  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  /**
   * Get the longitude of the provider location.
   *
   * @return The longitude of the provider location
   */
  public BigDecimal getLongitude() {
    return longitude;
  }

  /**
   * Set the longitude of the provider location.
   *
   * @param longitude The longitude of the provider location
   */
  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }
}
