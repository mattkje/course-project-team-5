package no.ntnu.courses.coursesapi.api.securityConfiguration.user;

import no.ntnu.courses.coursesapi.api.securityConfiguration.Role;

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
public class User {

    private final String userName; //Should always be final, do not change! >:(
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String password;
    private boolean enabled;
    private final LocalDate createdAt;

    private LocalDate updatedAt;
    private String phoneNumber;

    /**
     * Creates an instance of User with all fields.
     *
     * @param userName a username
     * @param firstName First name
     * @param lastName Last name
     * @param email Email
     * @param role User role
     * @param password Password
     * @param enabled Is user active //TODO: Is this necessary, maybe remove?
     * @param phoneNumber Phone number
     */
    public User(String userName, String firstName, String lastName, String email, Role role, String password,
                boolean enabled, String phoneNumber) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.password = password;
        this.enabled = enabled;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.phoneNumber = phoneNumber;
    }

    /**
     * Creates an instance of User with Only required fields.
     *
     * @param userName a username
     * @param firstName First name
     * @param lastName Last name
     * @param email Email
     * @param role User role
     * @param password Password
     * @param enabled Is user active //TODO: Is this necessary, maybe remove?
     */
    public User(String userName, String firstName, String lastName, String email, Role role, String password,
                boolean enabled) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.password = password;
        this.enabled = enabled;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }


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

}
