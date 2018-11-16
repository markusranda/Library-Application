package no.ntnu.datamod.data;


public class Employee {

  private long idEmployee;
  private String fname;
  private String lname;
  private String address;
  private String phone;
  private String accountNumber;
  private long ssn;
  private String position;
  private long idBranch;

  public Employee(long idEmployee, String fname, String lname, String address, String phone, String accountNumber, long ssn, String position, long idBranch) {
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

  public long getIdEmployee() {
    return idEmployee;
  }

  public void setIdEmployee(long idEmployee) {
    this.idEmployee = idEmployee;
  }


  public String getFname() {
    return fname;
  }

  public void setFname(String fname) {
    this.fname = fname;
  }


  public String getLname() {
    return lname;
  }

  public void setLname(String lname) {
    this.lname = lname;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }


  public long getSsn() {
    return ssn;
  }

  public void setSsn(long ssn) {
    this.ssn = ssn;
  }


  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }


  public long getIdBranch() {
    return idBranch;
  }

  public void setIdBranch(long idBranch) {
    this.idBranch = idBranch;
  }

}
