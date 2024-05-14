package no.ntnu.api.keywords;

import jakarta.persistence.*;

/**
 * This class represents the Keywords entity. It is used to store keywords in the database.
 * Each keyword has an id and a name.
 */
@Entity
@Table(name = "keywords")
public class Keywords {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int keywordId;

  @Column
  private String keywordName;

  /**
   * Empty constructor needed for JPA.
   */
  public Keywords() {
  }

  /**
   * Set the id of the keyword.
   *
   * @param keyword_id The id of the keyword
   */
  public void setId(int keyword_id) {
    this.keywordId = keyword_id;
  }

  /**
   * Get the id of the keyword.
   *
   * @return The id of the keyword
   */
  public int getId() {
    return keywordId;
  }

  /**
   * Get the name of the keyword.
   *
   * @return The name of the keyword
   */
  public String getKeyword() {
    return keywordName;
  }

  /**
   * Set the name of the keyword.
   *
   * @param keywordName The name of the keyword
   */
  public void setKeyword(String keywordName) {
    this.keywordName = keywordName;
  }
}
