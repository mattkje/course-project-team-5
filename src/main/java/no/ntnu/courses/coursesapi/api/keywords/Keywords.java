package no.ntnu.courses.coursesapi.api.keywords;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "keywords")
public class Keywords {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int keywordId;

  @Column(name = "keyword_name")
  private String keywordName;


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
