package no.ntnu.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import no.ntnu.api.course.Course;
import no.ntnu.api.role.Role;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Represents a user in the application.
 * This class encapsulates information about a user, including their username, name, email, role, password,
 * account status, creation date, last update date and subscription expiry date.
 * Users can have different roles such as ADMIN, PRO, or USER, each with specific permissions and privileges.
 * The enabled flag indicates whether the user's account is active.
 * The createdAt field stores the date when the user account was created, and updatedAt stores the date
 * when the user account was last updated.
 * Users may have a phone number associated with their account for communication purposes.
 * Additionally, the subscriptionExpire field stores the date when the user's subscription expires.
 * The users are able to hold a set of courses that they are enrolled in. The courses are represented by the Course class.
 */


@Entity
@Schema(name = "User", description = "A user in the application")
@Table(name = "users")
public class User {

    @Id
    @Schema(description = "The user ID", example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "The username of the user", example = "user123")
    @Column(length = 16)
    private String username;

    @Schema(description = "The email address of the user")
    @Column(length = 45)
    private String email;

    @Schema(description = "The password of the user")
    @Column(length = 64)
    private String password;

    @Schema(description = "The first name of the user")
    @Column(name = "first_name", length = 20)
    private String firstName;

    @Schema(description = "The last name of the user")
    @Column(name = "last_name", length = 20)
    private String lastName;


    private boolean active = true;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_courses",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new LinkedHashSet<>();

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new LinkedHashSet<>();


    @Schema(description = "The date when the user account was created")
    @Column(name = "created_at")
    private Date createdAt;

    @Schema(description = "The date when the user account was last updated")
    @Column(name = "updated_at")
    private Date updatedAt;

    @Schema(description = "The phone number of the user")
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Schema(description = "When the user's subscription expires")
    @Column(name = "subscription_expire")
    private LocalDate subscriptionExpire;

    /**
     * Empty constructor needed for JPA.
     */
    public User() {
    }

    /**
     * Creates a new user with the specified username, email, password, first name, last name, and phone number.
     * This constructor is used to create dummy data.
     *
     * @param username The username of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param phoneNumber The phone number of the user.
     */
    public User(String username, String password, String email, String firstName, String lastName, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }


    /**
     * Retrieves the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the date when the user account was created.
     *
     * @return The date when the user account was created.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Retrieves the date when the user account was last updated.
     *
     * @return The date when the user account was last updated.
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Retrieves the phone number of the user.
     *
     * @return The phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the user username.
     *
     * @param username Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the user first name.
     *
     * @param firstName First name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the user last name.
     *
     * @param lastName Last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the user email.
     *
     * @param email Email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Sets a password for the user
     *
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the phone number for the user.
     *
     * @param phoneNumber Phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the user ID.
     *
     * @param id The user ID.
     */
    public void setUserId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the user ID.
     *
     * @return The user ID.
     */
    public int getUserId() {
        return id;
    }

    /**
     * Sets the date when the user account was created.
     */
    public void setCreatedAt(Date date) {
        this.createdAt = date;
    }

    /**
     * Sets the date when the user account was last updated.
     */
    public void setUpdatedAt(Date date) {
        this.updatedAt = date;
    }

    /**
     * Checks if a user account is active.
     *
     * @return True if the user account is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active status of the user account.
     *
     * @param active True if the user account is active, false otherwise.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Sets the roles for the user.
     *
     * @param roles The roles for the user.
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Retrieves the roles for the user.
     *
     * @return The roles for the user.
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Adds a role to the user.
     *
     * @param role The role to add.
     */
    public void addRole(Role role) {
        roles.add(role);
    }

    /**
     * Sets the courses for the user.
     *
     * @param courses The courses for the user.
     */
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    /**
     * Retrieves the courses for the user.
     *
     * @return The courses for the user.
     */
    public Set<Course> getCourses() {
        return courses;
    }

    /**
     * Adds a course to the user.
     *
     * @param course The course to add.
     */
    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     * Retrieves the date when the user's subscription expires.
     *
     * @return The date when the user's subscription expires.
     */
    public LocalDate getSubscriptionExpire() {
        return subscriptionExpire;
    }

    /**
     * Sets the date when the user's subscription expires.
     *
     * @param subscriptionExpire The date when the user's subscription expires.
     */
    public void setSubscriptionExpire(LocalDate subscriptionExpire) {
        this.subscriptionExpire = subscriptionExpire;
    }
}