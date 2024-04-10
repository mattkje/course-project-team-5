package no.ntnu.courses.coursesapi.api.keywords;

import jakarta.persistence.*;

@Entity
@Table(name = "keywords")
public class Keywords {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int keywordId;

  @Column
  private String keywordName;

  public Keywords() { // This constructor is empty due to the use of the @Entity annotation

  }

  public void setId(int keyword_id) {
    this.keywordId = keyword_id;
  }

  public int getId() {
    return keywordId;
  }

  public String getKeyword() {
    return keywordName;
  }

  public void setKeyword(String keywordName) {
    this.keywordName = keywordName;
  }
}
