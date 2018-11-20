package no.ntnu.datamod.data;


public class Book extends Literature {

  private long idBook;
  private String authors;
  private String isbn;
  private String image;

  /**
   * Creates a new book. This constructor is to be used when there is a image.
   * @param publisher publisher
   * @param title title
   * @param idBook idBook
   * @param authors authors
   * @param isbn isbn
   * @param image image
   */
  public Book(String publisher, String title, long idBook, String authors, String isbn, String image) {
    super(publisher, title);
    this.idBook = idBook;
    this.authors = authors;
    this.isbn = isbn;
    this.image = image;
  }

  /**
   * Creates a new book. This constructor is to be used when there is no image.
   * @param publisher publisher
   * @param title title
   * @param idBook idBook
   * @param authors authors
   * @param isbn isbn
   */
  public Book(String publisher, String title, long idBook, String authors, String isbn) {
    super(publisher, title);
    this.idBook = idBook;
    this.authors = authors;
    this.isbn = isbn;
  }

  @Override
  public String getImageURL() {
    return image;
  }

  public long getIdBook() {
    return idBook;
  }

  public void setIdBook(long idBook) {
    this.idBook = idBook;
  }

  public String getAuthors() {
    return authors;
  }

  public void setAuthors(String authors) {
    this.authors = authors;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

}
