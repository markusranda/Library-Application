package no.ntnu.datamod.data;


public class Branch {

  private long idBranch;
  private String name;
  private String address;

  public Branch(long idBranch, String name, String address) {
    this.idBranch = idBranch;
    this.name = name;
    this.address = address;
  }

  public long getIdBranch() {
    return idBranch;
  }

  public void setIdBranch(long idBranch) {
    this.idBranch = idBranch;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

}
