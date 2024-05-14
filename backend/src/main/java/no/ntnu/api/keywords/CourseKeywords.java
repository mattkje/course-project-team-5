package no.ntnu.api.keywords;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * This class represents the CourseKeywords entity.
 * It is used to store the relationship between courses and keywords in the database.
 * Each course keyword has an id, a course id and a keyword id.
 * A course can have multiple keywords, and a keyword can be associated with multiple courses.
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

    /**
     * Empty constructor needed for JPA.
     */
    public CourseKeywords() {
    }

    /**
     * Set the id of the keyword.
     *
     * @param keyword The id of the keyword
     */
    public void setKeywordObj(Keywords keyword) {
        this.keyword = keyword;
    }

    /**
     * Get the id of the keyword.
     *
     * @return The id of the keyword
     */
    public Keywords getKeywordObj() {
        return keyword;
    }

    /**
     * Set the id of the course.
     *
     * @param keywordName The id of the course
     */
    public void setCourseId(int keywordName) {
        this.courseId = keywordName;
    }

    /**
     * Get the id of the course.
     *
     * @return The id of the course
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * Get the id of the course keyword.
     *
     * @return The id of the course keyword
     */
    public Integer getCourseKeywordId() {
        return courseKeywordId;
    }

    /**
     * get the keyword id.
     *
     * @return The keyword id
     */
    @JsonIgnore
    public Integer getKeywordId() {
        return keyword.getId();
    }
}
