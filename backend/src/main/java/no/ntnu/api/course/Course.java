package no.ntnu.api.course;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;
import no.ntnu.api.provider.Provider;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course in the database.
 */
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @Schema(description = "The course id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    @Column()
    @Schema(description = "The course title")
    private String title;
    @Column(length = 100)
    @Schema(description = "The course category")
    private String category;
    @Column(length = 50)
    @Schema(description = "The course level")
    private String level;
    @Column(name = "start_date")
    @Schema(description = "The course start date")
    private LocalDate start_date;
    @Column(name = "end_date")
    @Schema(description = "The course end date")
    private LocalDate end_date;
    @Column(name = "course_size", length = 5)
    @Schema(description = "The course size")
    private double courseSize;
    @Column(name = "hours_per_week")
    @Schema(description = "The hours per week")
    private int hoursPerWeek;
    @Column(name = "related_certifications")
    @Schema(description = "The related certifications")
    private String relatedCertifications;
    @Column(name = "description", length = 6000)
    @Schema(description = "The course description")
    private String description;
    @Column
    @Schema(description = "The course image")
    private String image;
    @Column
    @Schema(description = "The course active status")
    private boolean active;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "course_providers",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "provider_id"))
    private List<Provider> providers = new ArrayList<>();

    public Course() { // This constructor is empty due to the use of the @Entity annotation

    }

    // Getters and setters
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public void setStartDate(LocalDate start_date) {
        this.start_date = start_date;
    }

    public void setEndDate(LocalDate end_date) {
        this.end_date = end_date;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getLevel() {
        return level;
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

    public int getCourseId() {
        return courseId;
    }

    public String getImage() {
        return image;
    }

    public Long getId() {
        return (long) courseId;
    }

    public String getName() {
        return title;
    }

    public LocalDate getStartDate() {
        return start_date;
    }
    public LocalDate getEndDate() {
        return end_date;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
