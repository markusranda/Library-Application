package no.ntnu.datamod.data;


public class Employee {

  private int idEmployee;
  private String fname;
  private String lname;
  private String address;
  private String phone;
  private String accountNumber;
  private String ssn;
  private String position;
  private int idBranch;
  private String branch;
  private String username;

  public Employee(int idEmployee, String fname, String lname, String address, String phone, String accountNumber, String ssn, String position, int idBranch) {
    this.idEmployee = idEmployee;
    this.fname = fname;
    this.lname = lname;
    this.address = address;
    this.phone = phone;
    this.accountNumber = accountNumber;
    this.ssn = ssn;
    this.position = position;
    this.idBranch = idBranch;
  }

  public Employee(int idEmployee, String fname, String lname, String address, String phone,
                  String accountNumber, String ssn, String position, String branch, String username) {
    this.idEmployee = idEmployee;
    this.fname = fname;
    this.lname = lname;
    this.address = address;
    this.phone = phone;
    this.accountNumber = accountNumber;
    this.ssn = ssn;
    this.position = position;
    this.branch = branch;
    this.username = username;
  }

  public String getBranch() {
    return branch;
  }


  public String getUsername() {
    return username;
  }


  public int getIdEmployee() {
    return idEmployee;
  }


  public String getFname() {
    return fname;
  }


  public String getLname() {
    return lname;
  }


  public String getAddress() {
    return address;
  }


  public String getPhone() {
    return phone;
  }


  public String getAccountNumber() {
    return accountNumber;
  }


  public String getSsn() {
    return ssn;
  }


  public String getPosition() {
    return position;
  }


  public int getIdBranch() {
    return idBranch;
  }

}
