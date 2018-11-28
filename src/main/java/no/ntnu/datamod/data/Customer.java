package no.ntnu.datamod.data;


public class Customer {

  private int idCustomer;
  private String fname;
  private String lname;
  private String address;
  private String phone;

  public Customer(int idCustomer, String fname, String lname, String address, String phone) {
    this.idCustomer = idCustomer;
    this.fname = fname;
    this.lname = lname;
    this.address = address;
    this.phone = phone;
  }


  public int getIdCustomer() {
    return idCustomer;
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
}
