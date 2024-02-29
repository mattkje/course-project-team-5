package no.ntnu.courses.coursesapi.api.provider;

import jakarta.persistence.*;

@Entity
@Table(name = "providers") // replace with your new table name
public class Provider {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int providerId;

  @Column
  private String name;

  public int getProviderId() {
    return this.providerId;
  }

  public String getName() {
    return this.name;
  }

  public void setProviderId(int providerId) {
    this.providerId = providerId;
  }

  public void setName(String name) {
    this.name = name;
  }
}