package no.ntnu.courses.coursesapi.api.user;

import io.swagger.v3.oas.annotations.media.Schema;
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



public class RegistrationRequest {

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;



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

}