package no.ntnu.courses.coursesapi.api.user;

import jakarta.persistence.*;
import java.util.Date;


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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", length = 16)
    private String userName;

    @Column(length = 45)
    private String email;

    @Column(length = 64)
    private String password;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column
    private byte role;

    @Column
    private boolean enabled;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "phone_number", length = 20)
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
    public Byte getRole() {
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
     * Sets the role of the user to the specified role.
     * The available roles are: ADMIN, MODERATOR, and USER.
     * To assign the user the ADMIN role, pass Role.ADMIN as the argument.
     *
     * @param role The role to assign to the user (ADMIN, MODERATOR, USER)
     */
    public void setRole(Byte role) {
        this.role = role;

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
     * Sets the status of the user. Disable if user is not active for a
     * certain amount of time.
     *
     * @param enabled User status.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets the phone number for the user.
     *
     * @param phoneNumber Phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}