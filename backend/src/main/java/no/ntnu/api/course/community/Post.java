package no.ntnu.api.course.community;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * Represents a community course in the database.
 */
@Entity
@Table(name = "community_posts")
public class Post {
    @Id
    @Schema(description = "The course active status")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column()
    @Schema(description = "The post title")
    private String title;
    @Column()
    @Schema(description = "The post author")
    private String author;
    @Column(length = 100)
    @Schema(description = "The post category")
    private String category;
    @Column(length = 100)
    @Schema(description = "The post description")
    private String description;
    @Column(length = 6000)
    @Schema(description = "The post content")
    private String content;
    @Column()
    @Schema(description = "The post post date")
    private Date postDate;
    @Column
    @Schema(description = "The post image")
    private String image;

    public Post() { // This constructor is empty due to the use of the @Entity annotation

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

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}
