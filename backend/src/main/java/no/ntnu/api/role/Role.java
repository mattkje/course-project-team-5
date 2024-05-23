package no.ntnu.api.role;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import no.ntnu.api.user.User;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity class for a role.
 * A role represents a set of permissions and privileges that a user can have. Roles are used to manage access control.
 * Each user can have one or more roles assigned to them. Each role contain an id, a name, and a set of users that have
 * that role.
 */
@Entity(name = "roles")
@Table(name = "roles")
public class Role {
    @Schema(description = "The role id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "The role name")
    private String name;

    @Schema(description = "The users that have this role")
    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new LinkedHashSet<>();

    /**
     * Empty constructor needed for JPA.
     */
    public Role() {
    }

    /**
     * Constructor for creating a new role with a name.
     *
     * @param name The name of the role
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * Get the id of the role.
     *
     * @return The id of the role
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the role.
     *
     * @param id The id of the role
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the users that have this role.
     *
     * @return The users that have this role
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Set the users to have this role.
     *
     * @param users The users to have this role
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Get the name of the role.
     *
     * @return The name of the role
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the role.
     *
     * @param name The name of the role
     */
    public void setName(String name) {
        this.name = name;
    }
}
