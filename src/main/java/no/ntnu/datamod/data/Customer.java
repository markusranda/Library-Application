package no.ntnu.datamod.data;


public class Customer {

  private long idCustomer;
  private String fname;
  private String lname;
  private String address;
  private String phone;

  public Customer(long idCustomer, String fname, String lname, String address, String phone) {
    this.idCustomer = idCustomer;
    this.fname = fname;
    this.lname = lname;
    this.address = address;
    this.phone = phone;
  }


  public long getIdCustomer() {
    return idCustomer;
  }

  public void setIdCustomer(long idCustomer) {
    this.idCustomer = idCustomer;
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

}
