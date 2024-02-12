package no.ntnu.courses.coursesapi.api.course;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productID;
    @Column()
    private String title;
    @Column(length = 100)
    private String category;
    @Column()
    private String keywords;
    @Column(length = 50)
    private String level;
    @Column(name = "closest_course_session")
    private String closestCourseSession;
    @Column(name = "course_size", length = 5)
    private double courseSize;
    @Column(name = "hours_per_week")
    private int hoursPerWeek;
    @Column(name = "related_certifications")
    private String relatedCertifications;
    @Column(name = "description", length = 6000)
    private String description;
    @Column
    private String image;



    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setClosestCourseSession(String closestCourseSession) {
        this.closestCourseSession = closestCourseSession;
    }

    public void setCourseSize(int courseSize) {
        this.courseSize = courseSize;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public void setRelatedCertifications(String relatedCertifications) {
        this.relatedCertifications = relatedCertifications;
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

    public String getKeywords() {
        return keywords;
    }

    public String getLevel() {
        return level;
    }

    public String getClosestCourseSession() {
        return closestCourseSession;
    }

    public double getCourseSize() {
        return courseSize;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public String getRelatedCertifications() {
        return relatedCertifications;
    }

    public String getDescription() {
        return description;
    }

    public int getProductID() {
        return productID;
    }

    public String getImage() {
        return image;
    }
}