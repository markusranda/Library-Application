package no.ntnu.datamod.data;


public class Branch {

  private int idBranch;
  private String name;
  private String address;

  public Branch(int idBranch, String name, String address) {
    this.idBranch = idBranch;
    this.name = name;
    this.address = address;
  }

  public int getIdBranch() {
    return idBranch;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }
}
