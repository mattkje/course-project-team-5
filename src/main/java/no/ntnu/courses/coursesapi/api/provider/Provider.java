package no.ntnu.courses.coursesapi.api.provider;


import jakarta.persistence.*;
import no.ntnu.courses.coursesapi.api.course.Course;


@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int providerId;
    @Column(name = "course_id")
    private int courseId;
    @Column
    private String name;
    @Column
    private int price;
    @Column
    private String currency;


    public String getName() {
        return this.name;
    }

    public int getProviderId() {
        return this.providerId;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public int getPrice() {
        return this.price;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
