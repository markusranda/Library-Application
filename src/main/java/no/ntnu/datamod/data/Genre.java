package no.ntnu.datamod.data;


public class Genre {

  private long idGenre;
  private String name;

  public Genre(long idGenre, String name) {
    this.idGenre = idGenre;
    this.name = name;
  }

  public long getIdGenre() {
    return idGenre;
  }

  public void setIdGenre(long idGenre) {
    this.idGenre = idGenre;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
