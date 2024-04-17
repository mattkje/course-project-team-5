package no.ntnu.api.course.community;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a community course in the database.
 */
@Entity
@Table(name = "community_courses")
public class CommunityCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column()
    private String title;
    @Column()
    private String author;
    @Column(length = 100)
    private String category;
    @Column(length = 100)
    private String description;
    @Column(length = 6000)
    private String content;
    @Column
    private String image;

    public CommunityCourse() { // This constructor is empty due to the use of the @Entity annotation

    }

    // Getters and setters
    public void setCourseId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getCourseId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
