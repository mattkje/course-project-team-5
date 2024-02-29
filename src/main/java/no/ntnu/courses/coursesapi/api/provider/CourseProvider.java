package no.ntnu.courses.coursesapi.api.provider;


import jakarta.persistence.*;


@Entity
@Table(name = "course_providers")
public class CourseProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_provider_id")
    private int courseProviderId;
    @Column(name = "course_id")
    private int courseId;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @Column
    private float price;
    @Column
    private String currency;

    public int getCourseProviderId() {
        return this.courseProviderId;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public float getPrice() {
        return this.price;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCourseProviderId(int courseProviderId) {
        this.courseProviderId = courseProviderId;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getName() {
        return provider.getName();
    }

    public void setName(String providerName) {
        this.provider.setName(providerName);
    }
}
