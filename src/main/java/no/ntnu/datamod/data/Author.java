package no.ntnu.datamod.data;


public class Author {

  private int idAuthors;
  private String lName;
  private String fName;

  public Author(int idAuthors, String lName, String fName) {
    this.idAuthors = idAuthors;
    this.lName = lName;
    this.fName = fName;
  }

  public long getIdAuthors() {
    return idAuthors;
  }

  public String getLName() {
    return lName;
  }

  public String getFName() {
    return fName;
  }

}
