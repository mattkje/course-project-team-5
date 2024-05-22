package no.ntnu.api.config;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import no.ntnu.api.role.Role;
import no.ntnu.api.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Contains authentication information, needed by UserDetailsService.
 */
public class AccessUserDetails implements UserDetails {
  private final String username;
  private final String password;
  private final boolean isActive;
  private final List<GrantedAuthority> authorities = new LinkedList<>();

  /**
   * Create access object.
   *
   * @param user Teh user to copy data from
   */
  public AccessUserDetails(User user) {
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.isActive = user.isActive();
    this.convertRoles(user.getRoles());
  }

  /**
   * Convert roles to authorities.
   *
   * @param roles The roles to convert
   */
  private void convertRoles(Set<Role> roles) {
    authorities.clear();
    for (Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
  }

  /**
   * Get the username.
   *
   * @return The username
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  /**
   * Get the password.
   *
   * @return The password
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Get the username.
   *
   * @return The username
   */
  @Override
  public String getUsername() {
    return username;
  }

  /**
   * Check if the account is active.
   *
   * @return True if the account is active
   */
  @Override
  public boolean isAccountNonExpired() {
    return isActive;
  }

  /**
   * Check if the account is locked.
   *
   * @return True if the account is not locked
   */
  @Override
  public boolean isAccountNonLocked() {
    return isActive;
  }

  /**
   * Check if the credentials are expired.
   *
   * @return True if the credentials are not expired
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return isActive;
  }

  /**
   * Check if the account is enabled.
   *
   * @return True if the account is enabled
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}