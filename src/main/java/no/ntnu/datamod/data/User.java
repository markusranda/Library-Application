package no.ntnu.datamod.data;


public class User {

  private String username;
  private String password;
  private String usertype;

  /**
   * Constructs a new User.
   *
   * @param username username
   * @param password password
   * @param usertype the type of user for instance "administrator" or "librarian"
   */
  public User(String username, String password, String usertype) {
    this.username = username;
    this.password = password;
    this.usertype = usertype;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getUsertype() {
    return usertype;
  }

}
