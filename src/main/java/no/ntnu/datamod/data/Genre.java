package no.ntnu.datamod.data;


public class Genre {

  private int idGenre;
  private String name;

  public Genre(int idGenre, String name) {
    this.idGenre = idGenre;
    this.name = name;
  }

  public int getIdGenre() {
    return idGenre;
  }

  public String getName() {
    return name;
  }

}
