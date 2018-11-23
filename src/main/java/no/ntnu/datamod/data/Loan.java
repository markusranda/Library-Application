package no.ntnu.datamod.data;


import java.sql.Date;

public class Loan {

  private long idLoans;
  private java.sql.Date loanDate;
  private java.sql.Date loanDue;
  private long idBook;
  private String username;

  public Loan(long idLoans, Date loanDate, Date loanDue, long idBook, String username) {
    this.idLoans = idLoans;
    this.loanDate = loanDate;
    this.loanDue = loanDue;
    this.idBook = idBook;
    this.username = username;
  }


  public long getIdLoans() {
    return idLoans;
  }

  public void setIdLoans(long idLoans) {
    this.idLoans = idLoans;
  }


  public java.sql.Date getLoanDate() {
    return loanDate;
  }

  public void setLoanDate(java.sql.Date loanDate) {
    this.loanDate = loanDate;
  }


  public java.sql.Date getLoanDue() {
    return loanDue;
  }

  public void setLoanDue(java.sql.Date loanDue) {
    this.loanDue = loanDue;
  }


  public long getIdBook() {
    return idBook;
  }

  public void setIdBook(long idBook) {
    this.idBook = idBook;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
