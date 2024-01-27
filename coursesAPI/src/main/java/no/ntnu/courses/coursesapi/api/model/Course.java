package no.ntnu.courses.coursesapi.api.model;

import java.util.Date;
import java.util.List;

public class Course {
    private int productID;
    private String title;
    private String category;
    private List<String> keywords;
    private String level;
    private Date closestCourseSession;
    private int ects;
    private int hoursPerWeek;
    private List<String> relatedCertifications;
    private CourseProviders providers;
    private String description;

    public Course(int productID, String title, String category, List<String> keywords, String level,
                  Date closestCourseSession, int ects, int hoursPerWeek, List<String> relatedCertifications,
                  CourseProviders providers, String description) {
        this.productID = productID;
        this.title = title;
        this.category = category;
        this.keywords = keywords;
        this.level = level;
        this.closestCourseSession = closestCourseSession;
        this.ects = ects;
        this.hoursPerWeek = hoursPerWeek;
        this.relatedCertifications = relatedCertifications;
        this.providers = providers;
        this.description = description;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setClosestCourseSession(Date closestCourseSession) {
        this.closestCourseSession = closestCourseSession;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public void setRelatedCertifications(List<String> relatedCertifications) {
        this.relatedCertifications = relatedCertifications;
    }

    public void setProviders(CourseProviders providers) {
        this.providers = providers;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public String getLevel() {
        return level;
    }

    public Date getClosestCourseSession() {
        return closestCourseSession;
    }

    public int getEcts() {
        return ects;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public List<String> getRelatedCertifications() {
        return relatedCertifications;
    }

    public CourseProviders getProviders() {
        return providers;
    }

    public String getDescription() {
        return description;
    }

    public int getProductID() {
        return productID;
    }
}