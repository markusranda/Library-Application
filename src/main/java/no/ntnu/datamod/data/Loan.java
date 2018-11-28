package no.ntnu.datamod.data;


import java.sql.Date;

public class Loan {

  private int idBook;
  private int idLoan;
  private int idBranch;
  private String library;
  private String bookTitle;
  private String authors;
  private Date loanDate;
  private Date loanDue;
  private String username;
  private long remainingDays;
  private long fine;

  public Loan(int idLoans, Date loanDate, Date loanDue, int idBook, String username) {
    this.idLoan = idLoans;
    this.loanDate = loanDate;
    this.loanDue = loanDue;
    this.idBook = idBook;
    this.username = username;
  }

  public Loan(int idLoan, Date loanDate, Date loanDue, int idBook, String username, int idBranch, String library,
              String bookTitle, String authors, long remainingDays, long fine) {
    this.idBook = idBook;
    this.idLoan = idLoan;
    this.idBranch = idBranch;
    this.library = library;
    this.bookTitle = bookTitle;
    this.authors = authors;
    this.loanDate = loanDate;
    this.loanDue = loanDue;
    this.username = username;
    this.remainingDays = remainingDays;
    this.fine = fine;
  }

  public int getIdBook() {
    return idBook;
  }

  public int getIdLoan() {
    return idLoan;
  }

  public int getIdBranch() {
    return idBranch;
  }

  public String getLibrary() {
    return library;
  }

  public String getBookTitle() {
    return bookTitle;
  }

  public String getAuthors() {
    return authors;
  }

  public Date getLoanDate() {
    return loanDate;
  }

  public Date getLoanDue() {
    return loanDue;
  }

  public String getUsername() {
    return username;
  }

  public long getRemainingDays() {
    return remainingDays;
  }

  public long getFine() {
    return fine;
  }
}
