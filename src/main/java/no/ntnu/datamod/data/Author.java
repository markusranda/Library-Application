package no.ntnu.datamod.data;


public class Author {

  private long idAuthors;
  private String lName;
  private String fName;

  public Author(long idAuthors, String lName, String fName) {
    this.idAuthors = idAuthors;
    this.lName = lName;
    this.fName = fName;
  }

  public long getIdAuthors() {
    return idAuthors;
  }

  public void setIdAuthors(long idAuthors) {
    this.idAuthors = idAuthors;
  }


  public String getLName() {
    return lName;
  }

  public void setLName(String lName) {
    this.lName = lName;
  }


  public String getFName() {
    return fName;
  }

  public void setFName(String fName) {
    this.fName = fName;
  }

}
