package no.ntnu.api.config;

/**
 * The ChangePasswordRequest class is a class for storing
 * the request body of a change password request.
 */
public class ChangePasswordRequest {
  private String oldPassword;
  private String newPassword;

  public ChangePasswordRequest() {
  }

  public ChangePasswordRequest(String oldPassword, String newPassword) {
    this.oldPassword = oldPassword;
    this.newPassword = newPassword;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String password) {
    this.oldPassword = password;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}
