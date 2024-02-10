package no.ntnu.courses.coursesapi.api.securityConfiguration.user;

import jakarta.persistence.*;
import no.ntnu.courses.coursesapi.api.securityConfiguration.Role;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;



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
@Table(name = "users")
public class User {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true, length = 16)
    private String userName;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, length = 10)
    private Role role;

    @Column(nullable = false, length = 5)
    private boolean enabled;
    @Column(nullable = false, length = 10)
    private final LocalDate createdAt = LocalDate.now();

    @Column(nullable = false, length = 10)
    private LocalDate updatedAt;
    @Column(name = "phoneNumber", nullable = true, length = 20)
    private String phoneNumber;



    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
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
     * Retrieves the role of the user.
     *
     * @return The role of the user.
     */
    public Role getRole() {
        return role;
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
     * Checks if the user's account is enabled.
     *
     * @return true if the user's account is enabled, false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Retrieves the date when the user account was created.
     *
     * @return The date when the user account was created.
     */
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    /**
     * Retrieves the date when the user account was last updated.
     *
     * @return The date when the user account was last updated.
     */
    public LocalDate getUpdatedAt() {
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
     * Sets the user first name.
     *
     * @param firstName First name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        updateUser();
    }

    /**
     * Sets the user last name.
     *
     * @param lastName Last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
        updateUser();
    }

    /**
     * Sets the user email.
     *
     * @param email Email
     */
    public void setEmail(String email) {
        this.email = email;
        updateUser();
    }


    /**
     * Sets the role of the user to the specified role.
     * The available roles are: ADMIN, MODERATOR, and USER.
     * To assign the user the ADMIN role, pass Role.ADMIN as the argument.
     *
     * @param role The role to assign to the user (ADMIN, MODERATOR, USER)
     */
    public void setRole(Role role) {
        this.role = role;
        updateUser();
    }

    /**
     * Sets a password for the user
     *
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password;
        updateUser();
    }

    /**
     * Sets the status of the user. Disable if user is not active for a
     * certain amount of time.
     *
     * @param enabled User status.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        updateUser();
    }

    /**
     * Sets the phone number for the user.
     *
     * @param phoneNumber Phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        updateUser();
    }

    /**
     * Sets user updated status to now.
     * This method should be called for every change.
     */
    public void updateUser() {
        this.updatedAt = LocalDate.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
