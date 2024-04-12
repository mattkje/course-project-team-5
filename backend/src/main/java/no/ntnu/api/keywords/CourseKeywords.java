package no.ntnu.api.keywords;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Contains the keywords for a course
 */
@Entity
@Table(name = "course_keywords")
public class CourseKeywords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_keyword_id")
    private int courseKeywordId;

    @Column(name = "course_id")
    private int courseId;

    @ManyToOne
    @JoinColumn(name = "keyword_id")
    private Keywords keyword;

    public CourseKeywords() { // This constructor is empty due to the use of the @Entity annotation

    }

    // Getters and setters
    public void setKeywordObj(Keywords keyword) {
        this.keyword = keyword;
    }

    public void setKeywordId(String keyword) {
        this.keyword.setKeyword(keyword);
    }

    public Keywords getKeywordObj() {
        return keyword;
    }

    public void setCourseId(int keywordName) {
        this.courseId = keywordName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public Integer getCourseKeywordId() {
        return courseKeywordId;
    }

    @JsonIgnore
    public Integer getKeywordId() {
        return keyword.getId();
    }
}
