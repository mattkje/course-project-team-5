package no.ntnu.api.user;

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
import no.ntnu.api.role.Role;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Represents a user in the application.
 * This class encapsulates information about a user, including their username, name, email, role, password,
 * account status, creation date, and last update date.
 * Users can have different roles such as ADMIN, MODERATOR, or USER, each with specific permissions and privileges.
 * The enabled flag indicates whether the user's account is active.
 * The createdAt field stores the date when the user account was created, and updatedAt stores the date
 * when the user account was last updated.
 * Additionally, users may have a phone number associated with their account for communication purposes.
 * TODO: More optional fields?
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

    //This is needed for JPA
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


    public void setUserId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return id;
    }

    public void setCreatedAt() {
        this.createdAt = new Date();
    }

    public void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

}