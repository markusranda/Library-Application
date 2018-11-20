package no.ntnu.datamod.data;


public class User {

  private long idUser;
  private String username;
  private String password;
  private String usertype;
  private long idCustomer;
  private long idEmployee;

  /**
   * Constructs a new User.
   *
   * @param idUser user ID
   * @param username username
   * @param password password
   * @param usertype the type of user for instance "administrator" or "librarian"
   */
  public User(long idUser, String username, String password, String usertype) {
    this.idUser = idUser;
    this.username = username;
    this.password = password;
    this.usertype = usertype;
    idCustomer = 0;
    idEmployee = 0;
  }


  public long getIdUser() {
    return idUser;
  }

  public void setIdUser(long idUser) {
    this.idUser = idUser;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getUsertype() {
    return usertype;
  }

  public void setUsertype(String usertype) {
    this.usertype = usertype;
  }


  public long getIdCustomer() {
    return idCustomer;
  }

  public void setIdCustomer(long idCustomer) {
    this.idCustomer = idCustomer;
  }


  public long getIdEmployee() {
    return idEmployee;
  }

  public void setIdEmployee(long idEmployee) {
    this.idEmployee = idEmployee;
  }

}
